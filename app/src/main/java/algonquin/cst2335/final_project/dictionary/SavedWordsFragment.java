package algonquin.cst2335.final_project.dictionary;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.final_project.R;
import algonquin.cst2335.final_project.databinding.DictionarySavedWordsBinding;




public class SavedWordsFragment extends Fragment {

    private DictionaryActivity activity = null;

    private final List<Word> words;


    public SavedWordsFragment(List<Word> words) {
        this.words = words;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.activity = (DictionaryActivity) getActivity();

        DictionarySavedWordsBinding binding = DictionarySavedWordsBinding.inflate(inflater);

        binding.dictSavedRecycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.dictSavedRecycleview.setAdapter(new RecyclerView.Adapter<SavedWordsFragment.ViewHolder>() {


            @NonNull
            @Override
            public SavedWordsFragment.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(getContext()).inflate(R.layout.dictionary_word, parent, false);
                SavedWordsFragment.ViewHolder vh = new SavedWordsFragment.ViewHolder(v);
                return vh;
            }


            @Override
            public void onBindViewHolder(@NonNull SavedWordsFragment.ViewHolder holder, int position) {
                final Word word = SavedWordsFragment.this.words.get(position);
                holder.wordText.setOnClickListener((view -> {
                    Log.i("Saved", "Clicked: " + word.getWord());
                    SavedWordsFragment.this.activity.setFragment(new DefinitionsListFragment(word.getWord(), word.getDefinitions()), true);
                }));
                holder.wordText.setText(word.getWord());
            }


            @Override
            public int getItemCount() {
                final SavedWordsFragment savedW = SavedWordsFragment.this;
                return savedW.words.size();
            }
        });

        return binding.getRoot();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView wordText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.wordText = itemView.findViewById(R.id.wordTextView);
        }
    }
}