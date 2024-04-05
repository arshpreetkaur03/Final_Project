package algonquin.cst2335.final_project.dictionary;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import algonquin.cst2335.final_project.R;
import algonquin.cst2335.final_project.databinding.DictionaryDefinitionListBinding;


public class DefinitionsListFragment extends Fragment {

    final List<Definition> definitions;
    final String word;

    DictionaryActivity activity = null;

    public DefinitionsListFragment(String word, List<Definition> definitions) {
        this.definitions = definitions;
        this.word = word;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.activity = (DictionaryActivity) getActivity();
        DefinitionDao dao = this.activity.getDefinitionDao();

        DictionaryDefinitionListBinding binding = DictionaryDefinitionListBinding.inflate(inflater);
        binding.savedTitle.setText(word.toUpperCase());

        binding.dictToggleSaved.setOnClickListener(view -> {
            new Thread(() -> {
                List<Definition> existing = dao.getDefinitionsForWord(this.word);
                if (!existing.isEmpty()) {
                    dao.deleteDefinitionsForWord(this.word);
                    activity.runOnUiThread(() -> {
                        Snackbar.make(binding.getRoot(), "Saved word deleted.", Snackbar.LENGTH_LONG)
                                .setAction("Undo", v -> {
                                    new Thread(() -> {
                                        dao.insertAll(existing);
                                        activity.runOnUiThread(() -> {
                                            binding.dictRecyclerView.getAdapter().notifyDataSetChanged();
                                        });
                                    }).start();
                                })
                                .show();
                    });
                } else {
                    dao.insertAll(this.definitions);
                    activity.runOnUiThread(() -> {
                        Snackbar.make(binding.getRoot(), "Word saved.", Snackbar.LENGTH_LONG)
                                .setAction("Undo", v -> {
                                    new Thread(() -> {
                                        dao.deleteDefinitionsForWord(this.word);
                                        activity.runOnUiThread(() -> {
                                            binding.dictRecyclerView.getAdapter().notifyDataSetChanged();
                                        });
                                    }).start();
                                })
                                .show();
                    });
                }
            }).start();
        });



        binding.dictRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.dictRecyclerView.setAdapter(new DefinitionsAdapter(definitions));

        return binding.getRoot();
    }

    private static class DefinitionsAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final List<Definition> definitions;

        public DefinitionsAdapter(List<Definition> definitions) {
            this.definitions = definitions;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View vw = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_definition, parent, false);
            return new ViewHolder(vw);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textNum.setText(String.valueOf(position + 1));
            Definition def = definitions.get(position);
            holder.partOfSpeech.setText(def.getPartOfSpeech());
            holder.definitionParagraph.setText(def.getDefinition());
        }

        @Override
        public int getItemCount() {
            return definitions.size();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textNum;
        final TextView partOfSpeech;
        final TextView definitionParagraph;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNum = itemView.findViewById(R.id.definitionNumber);
            partOfSpeech = itemView.findViewById(R.id.definitionPartOfSpeech);
            definitionParagraph = itemView.findViewById(R.id.definitionParagraph);
        }
    }
}
