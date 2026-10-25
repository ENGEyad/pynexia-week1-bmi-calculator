package com.pynexia.week1.bmi;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends BaseActivity {
    private EditText height, weight;
    private TextView result, category;
    private boolean calculated;

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        showLayout(R.layout.activity_main);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        result = findViewById(R.id.result);
        category = findViewById(R.id.category);
        findViewById(R.id.calculate).setOnClickListener(view -> calculate());
        findViewById(R.id.reset).setOnClickListener(view -> { height.setText(""); weight.setText(""); height.setError(null); weight.setError(null); clearResult(); });
        TextWatcher watcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) { clearResult(); }
            public void afterTextChanged(Editable s) {}
        };
        height.addTextChangedListener(watcher);
        weight.addTextChangedListener(watcher);
    }

    @Override protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if (state.getBoolean("calculated")) calculate();
    }

    @Override protected void onSaveInstanceState(Bundle state) {
        state.putBoolean("calculated", calculated);
        super.onSaveInstanceState(state);
    }

    private void clearResult() {
        calculated = false;
        result.setText(R.string.result_empty);
        category.setText("");
    }

    private void calculate() {
        height.setError(null);
        weight.setError(null);
        double h = parse(height), w = parse(weight);
        boolean valid = true;
        if (!BmiCalculator.validWeight(w)) { weight.setError(getString(R.string.weight_error)); weight.requestFocus(); valid = false; }
        if (!BmiCalculator.validHeight(h)) { height.setError(getString(R.string.height_error)); height.requestFocus(); valid = false; }
        if (!valid) { clearResult(); return; }
        double bmi = BmiCalculator.calculate(h, w);
        result.setText(getString(R.string.result_format, bmi));
        int label = switch (BmiCalculator.category(bmi)) {
            case UNDERWEIGHT -> R.string.underweight;
            case NORMAL -> R.string.normal;
            case OVERWEIGHT -> R.string.overweight;
            case OBESE -> R.string.obese;
        };
        category.setText(label);
        calculated = true;
    }

    private double parse(EditText field) {
        try { return BmiCalculator.parseDecimal(field.getText().toString()); }
        catch (IllegalArgumentException error) { return Double.NaN; }
    }
}
