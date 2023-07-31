package com.nayan.doggle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class ImageClassifier {

    private static final String TAG = "ImageClassifier";
    private static final int IMAGE_WIDTH = 224;
    private static final int IMAGE_HEIGHT = 224;
    private static final int NUM_CHANNELS = 3;
    private static final int NUM_CLASSES = 10;

    private Context context;
    private Interpreter tflite;
    private ByteBuffer inputBuffer;
    private float[][] outputProbArray;
    private List<String> classLabels;

    public ImageClassifier(Context context) throws IOException {
        this.context = context.getApplicationContext();

        MappedByteBuffer modelFile = loadModelFile();
        tflite = new Interpreter(modelFile);

        inputBuffer = ByteBuffer.allocateDirect(IMAGE_WIDTH * IMAGE_HEIGHT * NUM_CHANNELS * 4);
        inputBuffer.order(ByteOrder.nativeOrder());
        outputProbArray = new float[1][NUM_CLASSES];

        classLabels = loadLabels(context);
    }

    private List<String> loadLabels(Context context) throws IOException {
        List<String> labels = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open("class_labels.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                labels.add(line);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return labels;
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd("dogmodel.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    public void classifyImage(Bitmap bitmap, ClassifierCallback callback) {
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_WIDTH, IMAGE_HEIGHT, true);
        convertBitmapToByteBuffer(resizedBitmap);

        tflite.run(inputBuffer, outputProbArray);

        int maxProbIndex = getMaxProbabilityIndex();
        float maxProb = outputProbArray[0][maxProbIndex];

        String breed = getClassLabel(maxProbIndex);
        callback.onClassificationResult(breed, maxProb);
    }

    private void convertBitmapToByteBuffer(Bitmap bitmap) {
        if (inputBuffer == null) {
            return;
        }
        inputBuffer.rewind();

        int[] intValues = new int[IMAGE_WIDTH * IMAGE_HEIGHT];
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        int pixel = 0;
        for (int i = 0; i < IMAGE_WIDTH; i++) {
            for (int j = 0; j < IMAGE_HEIGHT; j++) {
                final int val = intValues[pixel++];
                inputBuffer.putFloat(((val >> 16) & 0xFF) / 255.0f);
                inputBuffer.putFloat(((val >> 8) & 0xFF) / 255.0f);
                inputBuffer.putFloat((val & 0xFF) / 255.0f);
            }
        }
    }

    private int getMaxProbabilityIndex() {
        float maxProb = outputProbArray[0][0];
        int maxProbIndex = 0;
        for (int i = 1; i < NUM_CLASSES; i++) {
            if (outputProbArray[0][i] > maxProb) {
                maxProb = outputProbArray[0][i];
                maxProbIndex = i;
            }
        }
        return maxProbIndex;
    }

    private String getClassLabel(int classIndex) {
        if (classIndex >= 0 && classIndex < classLabels.size()) {
            return classLabels.get(classIndex);
        } else {
            return "Unknown";
        }
    }

    public interface ClassifierCallback {
        void onClassificationResult(String breed, float confidence);
    }


    public void loadImageFromGallery(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, requestCode);
    }

    public void captureImageFromCamera(Activity activity, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(intent, requestCode);
        } else {
            Log.e(TAG, "No camera app available.");
        }
    }

    public Bitmap processGalleryImage(Context context, Uri imageUri) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
        return bitmap;
    }

    public Bitmap processCameraImage(Intent data) {
        Bundle extras = data.getExtras();
        return (Bitmap) extras.get("data");
    }
}
