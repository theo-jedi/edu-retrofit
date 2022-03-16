package com.theost.retrofitapp.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theost.retrofitapp.databinding.ItemSuggestionBinding;

import java.util.ArrayList;
import java.util.List;

public class SuggestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> suggestions = new ArrayList<>();
    private boolean isEndOfWord = false;

    private final SuggestionListener suggestionListener;

    public SuggestionAdapter(SuggestionListener suggestionListener) {
        this.suggestionListener = suggestionListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSuggestionBinding binding = ItemSuggestionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding, suggestionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(suggestions.get(position), isEndOfWord);
    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    public void setData(List<String> suggestions, boolean isEndOfWord) {
        int oldItemCount = getItemCount();
        int newItemCount = suggestions.size();

        this.suggestions = new ArrayList<>(suggestions);
        this.isEndOfWord = isEndOfWord;

        if (oldItemCount < newItemCount) {
            notifyItemRangeInserted(oldItemCount, newItemCount);
        } else if (oldItemCount > newItemCount) {
            notifyItemRangeRemoved(newItemCount, oldItemCount);
        }
        notifyItemRangeChanged(0, newItemCount);
    }

    public void clearData() {
        int itemCount = getItemCount();
        this.suggestions = new ArrayList<>();
        notifyItemRangeRemoved(0, itemCount);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemSuggestionBinding binding;
        private final SuggestionListener suggestionListener;

        public ViewHolder(ItemSuggestionBinding binding, SuggestionListener suggestionListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.suggestionListener = suggestionListener;
        }

        public void bind(String word, boolean isEndOfWord) {
            binding.getRoot().setOnClickListener(view -> suggestionListener.onClick(word, isEndOfWord));
            binding.suggestion.setText(word);
        }
    }
}
