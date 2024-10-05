package com.example.herblife;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.herblife.ml.ModelUnquant;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class scanmushrooms extends AppCompatActivity {
    TextView result, confidence, result1,result2,v4,v5;
    ImageView img, back, picture, gallery, question;
    Button  more;
    Dialog mDialog, dDialog;
    boolean imageScanned = false;
    int imageSize = 224;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanmush);

        result2= findViewById(R.id.description2);
        result1 = findViewById(R.id.infooyster);
        gallery = findViewById(R.id.gallery12);
        result = findViewById(R.id.statusofoyster);
        confidence = findViewById(R.id.confidence);
        img = findViewById(R.id.plantimage1);
        picture = findViewById(R.id.scan);
        back = (ImageView) findViewById(R.id.btnback);
        v4=(TextView) findViewById(R.id.statusofoyster);
        v5=(TextView) findViewById(R.id.description2);
        more = findViewById(R.id.more);
        more.setVisibility(View.INVISIBLE);

        question =findViewById(R.id.quest);
        mDialog=new Dialog(this);
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.setContentView(R.layout.manual);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.show();
                Button close = mDialog.findViewById(R.id.buttonback);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                    }
                });

            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(cameraIntent, 3);

            }
        });

        back.setOnClickListener(view -> {
            Intent i = new Intent(scanmushrooms.this, MainActivity2.class);
            startActivity(i);
            finish();
        });
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch camera if we have permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M  ) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, 1);
                    } else {
                        //Request camera permission if we don't have it.
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                    }
                }
            }
        });
        more.setOnClickListener(view -> {
            String ne = result.getText().toString();
            String de = result1.getText().toString();
            String re = result2.getText().toString();

            MushroomDetails mushroomDetailsDialog = new MushroomDetails(scanmushrooms.this, ne, de, re);
            mushroomDetailsDialog.show();
        });

    }

    public void classifyImage(Bitmap image){
        try {
            ModelUnquant model = ModelUnquant.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            // get 1D array of 224 * 224 pixels in image
            int [] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

            // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
            int pixel = 0;
            for(int i = 0; i < imageSize; i++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            ModelUnquant.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();

            int maxPos = 0;
            int maxPos2 =0;
            int maxPos3 =0;
            float maxConfidence = 0;
            for(int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                    maxPos2 = i;
                    maxPos3 = i;
                }
            }

            String[] classes = {"Healthy Oyster Mushroom",
                    "Invalid Identification, not a mushroom",
                    "This mushroom has a disease called Brown Blotch",
                    "This mushroom has a disease called Green Mold",
                    "This mushroom has a disease called Soft Rot",
                    "This is a not an oyster Mushroom",

            };

                result.setText(classes[maxPos]);

            String[] classes2 = {"A healthy oyster mushroom features a firm and vibrant-colored cap, well-defined closely spaced gills, and a sturdy, unbruised stem. Its aroma is mild and fresh, and it should be harvested at the right time, just before the gills fully open. The substrate and growing environment should be clean, free from contamination, ensuring optimal flavor and nutritional quality.\n",
                    "Sorry, this item is not identified as a mushroom. Please try scanning a different object.\n",
                    "Brown Blotch, caused by the bacterium Pseudomonas tolaasii, manifests as distinct brownish spots on the caps of oyster mushrooms. These lesions typically appear as irregular, discolored blotches, often accompanied by a characteristic unpleasant odor. The affected areas may exhibit a slimy texture, and the overall appearance of the mushroom caps is marred by these discolorations. The brown blotches can vary in size and shape, creating a visually identifiable symptom that indicates the presence of this bacterial disease. Regular monitoring for these distinctive symptoms is crucial for early detection and effective management to minimize the impact on oyster mushroom cultivation.\n",
                    "Green Mold caused by Trichoderma spp. presents as a fluffy, greenish growth on the substrate or mushroom surface. The mold often has a cotton-like or powdery appearance and may spread rapidly in humid conditions. Characterized by its distinctive color, it can easily be distinguished from the desired white mycelium of oyster mushrooms. The contamination poses a threat to mushroom cultivation, as it competes for nutrients, inhibits mushroom growth, and may ultimately lead to yield loss if not addressed promptly. Effective prevention through proper pasteurization and sanitation practices is essential to curb the development of Trichoderma contamination.\n",
                    "Bacterial Soft Rot, caused by Pectobacterium carotovorum, manifests as distinctive soft, water-soaked lesions on the caps of oyster mushrooms. These lesions are characterized by a slimy texture and often appear in shades of brown or gray. The affected areas exhibit a weakened and deteriorated structure, leading to a loss of firmness in the mushroom tissue. Additionally, a foul odor may accompany the infection. This bacterial disease poses a risk to the overall quality of the oyster mushrooms, necessitating prompt identification and removal of infected specimens to prevent further spread within the cultivation environment.\n",
                    "Mushease only identify and analyze the health status of an oyster mushroom. Oyster Mushrooms have its unique features you could recognize, they typically have a broad, fan-shaped or oyster-shaped cap, ranging in color from white to shades of cream, beige, pink, or light gray, depending on the strain. The caps can measure anywhere from 2 to 10 inches (5 to 25 cm) in diameter. The gills, located on the underside of the cap, are closely spaced, white when young, and may become a creamy color as the mushroom matures. The stems are often short, thick, and centrally attached to the cap. Oyster mushrooms are known for their delicate and mild flavor, making them a popular choice in culinary applications.\n",

            };

            result1.setText(classes2[maxPos2]);

            String[] classes3 = {"Oyster Mushroom is detected healthy.\n"+
                    "To maintain healthy oyster mushrooms, ensure a controlled environment with stable temperature, high humidity, and good air circulation. Practice strict hygiene to prevent contamination, and harvest the mushrooms at the right time to preserve optimal quality. Regularly monitor and adjust environmental conditions, use clean harvesting tools, and address any signs of pests or diseases promptly for successful cultivation.\n",
                    "Oops! It looks like the scanned item is not a mushroom. Please try scanning a different object. Make sure that it is an oyster mushroom.\n"+
                    "For better results, try angling your camera to the front or side of the mushroom.",
                    "Symptoms: Brownish spots on the mushroom caps, often accompanied by an unpleasant odor.\n" +
                            "Some Tips: Good hygiene, proper sanitation, and maintaining optimal growing conditions can help prevent brown blotch.\n",
                    "Symptoms: Greenish mold on the substrate or mushroom surface.\n" +
                            "Some Tips: Maintain proper pasteurization of the substrate, ensure good ventilation, and practice strict hygiene to prevent contamination.\n\n",
                    "Symptoms: Soft, slimy, and water-soaked lesions on the mushroom caps.\n" +
                            "Some tips: Do strict sanitation practices and proper ventilation. Avoid harvesting mushrooms with water droplets on them. Maintain a clean growing environment and implement effective sanitation measures to preserve the health of the oyster mushroom crop.\n\n",
                    "The image you took was not an oyster mushroom, though it is healthy! So to keep it healthy, here are some tips!\n"+
                    "Maintaining a healthy mushroom crop involves providing a clean and controlled environment with appropriate temperature, humidity, and ventilation. Use sterile substrates, practice meticulous hygiene to prevent contamination, and monitor for pests or diseases. Timely harvesting, proper watering techniques, and consistent environmental conditions contribute to the overall health and successful cultivation of mushrooms.\n",
            };

            result2.setText(classes3[maxPos3]);

                String s = "";
                for (int i = 0; i < classes.length; i++) {
                    s += String.format("%s: %.1f%%\n",
                            classes[i],
                            confidences[i] * 100);
                }

                confidence.setText(s);
                model.close();

            imageScanned = true;

            // Make the "more" button visible
            more.setVisibility(View.VISIBLE);


        } catch (IOException e) {
            // TODO Handle the exception
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            assert data != null;
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            img.setImageBitmap(image);

            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            classifyImage(image);
        }
        else
        {
            if (requestCode == 3 && resultCode == RESULT_OK) {

                assert data != null;
                Uri dat = data.getData();
                Bitmap image2 = null;
                try {
                    image2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                img.setImageBitmap(image2);

                image2 = Bitmap.createScaledBitmap(image2, imageSize, imageSize, false);
                classifyImage(image2);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }


}
