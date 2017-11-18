/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */
package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText text = (EditText) findViewById(R.id.name);
        String name = text.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean addWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateChickBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean addChocolate = chocolateChickBox.isChecked();
        int price = calculatePrice(addWhippedCream, addChocolate);
        composeEmail(createOrderSummary(name, price, addWhippedCream, addChocolate),name);
        //displayMessage(priceMessage);
    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage;
        priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return (priceMessage);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method is called when the increase button is clicked.
     */
    public void increment(View view) {
        if (100 > quantity) {
            displayQuantity(++quantity);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "You cannot have more than 100 coffee";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
            return;
        }

    }

    /**
     * This method is called when the decrease button is clicked.
     */
    public void decrement(View view) {
        if (1 < quantity) {
            displayQuantity(--quantity);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "You cannot have less than 1 coffee";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
            return;
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @return the total price.
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int singlePrice = 5;
        if (addWhippedCream) {
            ++singlePrice;
        }
        if (addChocolate) {
            singlePrice += 2;
        }
        return (quantity * singlePrice);
    }

    /**
     * Send the content of order summary to the costumer with an email.
     */
    public void composeEmail(String text,String name) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
