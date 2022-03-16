package com.theost.retrofitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import com.theost.retrofitapp.databinding.ActivityMainBinding;
import com.theost.retrofitapp.recycler.SuggestionAdapter;
import com.theost.retrofitapp.retrofit.PredictedText;
import com.theost.retrofitapp.retrofit.PredictedTextDto;
import com.theost.retrofitapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private SuggestionAdapter adapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new SuggestionAdapter((word, isEndOfWord) -> {
            adapter.clearData();
            updateInputField(word, isEndOfWord);
        });

        binding.suggestionsList.setAdapter(adapter);

        Disposable disposable = Observable.create(emitter -> {
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    emitter.onNext(StringUtils.getLastWord(editable.toString().trim()));
                }
            };
            emitter.setCancellable(() -> binding.textInput.removeTextChangedListener(textWatcher));
            binding.textInput.addTextChangedListener(textWatcher);
        }).observeOn(Schedulers.io())
                .filter(o -> !((String) o).isEmpty())
                .distinctUntilChanged()
                .debounce(500, TimeUnit.MILLISECONDS)
                .switchMapSingle((Function<Object, SingleSource<?>>) text -> RetrofitClient.getInstance().getPredictedText(
                        (String) text,
                        RetrofitClient.API_LANGUAGE,
                        RetrofitClient.API_SUGGESTIONS_LIMIT,
                        RetrofitClient.API_KEY
                ))
                .map(predictedTextDto -> ((PredictedTextDto) predictedTextDto).mapToPredictedText())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(predictedText -> {
                    adapter.setData(predictedText.getWords(), predictedText.isEndOfWord());
                }, throwable -> {
                    showErrorToast(throwable.toString());
                    throwable.printStackTrace();
                });
        compositeDisposable.add(disposable);
    }

    private void showErrorToast(String error) {
        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
    }

    private void updateInputField(String word, boolean isEndOfWord) {
        StringBuilder stringBuilder = new StringBuilder();
        String input = binding.textInput.getText().toString().trim();
        if (!isEndOfWord) input = StringUtils.trimLastWord(input);
        stringBuilder.append(input).append(" ").append(word).append(" ");
        binding.textInput.setText(stringBuilder.toString());
        binding.textInput.setSelection(binding.textInput.getText().length());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}