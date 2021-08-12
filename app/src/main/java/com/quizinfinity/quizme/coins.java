package com.quizinfinity.quizme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


//import com.flutterwave.raveandroid.RaveConstants;
import com.flutterwave.raveandroid.RavePayActivity;
//import com.flutterwave.raveandroid.RavePayManager;
import java.util.UUID;

import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RaveUiManager;
import com.flutterwave.raveandroid.data.Utils;
import com.flutterwave.raveandroid.rave_core.models.SavedCard;
import com.flutterwave.raveandroid.rave_java_commons.Meta;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;
import com.flutterwave.raveandroid.rave_java_commons.SubAccount;
import com.flutterwave.raveandroid.rave_presentation.FeeCheckListener;
import com.flutterwave.raveandroid.rave_presentation.RaveNonUIManager;
import com.flutterwave.raveandroid.rave_presentation.RavePayManager;
import com.flutterwave.raveandroid.rave_presentation.card.Card;
import com.flutterwave.raveandroid.rave_presentation.card.CardPaymentCallback;
import com.flutterwave.raveandroid.rave_presentation.card.CardPaymentManager;
import com.flutterwave.raveandroid.rave_presentation.card.SavedCardsListener;
import com.flutterwave.raveandroid.rave_presentation.data.AddressDetails;
//import com.flutterwave.raveutils.verification.AVSVBVFragment;
//import com.flutterwave.raveutils.verification.OTPFragment;
//import com.flutterwave.raveutils.verification.PinFragment;
//import com.flutterwave.raveutils.verification.RaveVerificationUtils;

public class coins extends AppCompatActivity {
    Button btnOne, btnTwo;
    final int amount_1 = 1;
    final int amount_2 = 1;
    String email = "example@email.com";
    String fName = "FirstName";
    String lName = "LastName";
    String narration = "payment for food";
    String txRef;
    String country = "UG";
    String currency = "USD";

    final String publicKey = "[INSERT YOUR PUBLIC KEY]"; //Get your public key from your account
    final String encryptionKey = "[INSERT YOUR ENCRYPTION KEY]"; //Get your encryption key from your account

    Button one,two;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coins);
        one=findViewById(R.id.one);
        two=findViewById(R.id.two);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.one:
//                makePayment(amount_1); //calls payment method with amount 1
//                break;
//            case R.id.two:
//                makePayment(amount_2); //calls payment method with amount 2
//                break;
//        }
//    }
//
//    public void makePayment(int amount){
//        txRef = email +" "+  UUID.randomUUID().toString();
//
//        /*
//        Create instance of RavePayManager
//         */
//        new RavePayManager(this).setAmount(amount)
//                .setCountry(country)
//                .setCurrency(currency)
//                .setEmail(email)
//                .setfName(fName)
//                .setlName(lName)
//                .setNarration(narration)
//                .setPublicKey(publicKey)
//                .setEncryptionKey(encryptionKey)
//                .setTxRef(txRef)
//                .acceptAccountPayments(true)
//                .acceptCardPayments(true)
//                .acceptMpesaPayments(false)
//                .acceptGHMobileMoneyPayments(false)
//                .onStagingEnv(false).
//                allowSaveCardFeature(true)
//                .withTheme(R.style.DefaultPayTheme)
//                .initialize();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
//            String message = data.getStringExtra("response");
//            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
//                Toast.makeText(this, "SUCCESS " + message, Toast.LENGTH_SHORT).show();
//            } else if (resultCode == RavePayActivity.RESULT_ERROR) {
//                Toast.makeText(this, "ERROR " + message, Toast.LENGTH_SHORT).show();
//            } else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
//                Toast.makeText(this, "CANCELLED " + message, Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

}