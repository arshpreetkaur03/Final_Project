package algonquin.cst2335.final_project.dictionary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.final_project.R;
import algonquin.cst2335.final_project.databinding.DictionarySearchFragmentBinding;

public class SearchFragment extends Fragment {

    private DictionaryActivity activity = null;
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.activity = (DictionaryActivity) getActivity();

        SharedPreferences prefs = this.activity.getSharedPreferences("dictionary", Context.MODE_PRIVATE);
        final String search = prefs.getString("search", "");

        DictionarySearchFragmentBinding binding = DictionarySearchFragmentBinding.inflate(inflater);

        binding.searchBar.setText(search);

        // Initialize the request queue
        requestQueue = Volley.newRequestQueue(getContext());


        binding.searchButton.setOnClickListener((view) -> {
            final String input = binding.searchBar.getText().toString();

            // Save search input to SharedPreferences
            prefs.edit().putString("search", input).apply();

            // Check if the input is empty
            if (input.isEmpty()) {
                // Show error dialog if the input is empty
                new AlertDialog.Builder(getContext())
                        .setTitle("Error")
                        .setMessage("Please enter a word to search.")
                        .setNeutralButton("Ok", (dialog, i) -> dialog.dismiss())
                        .show();
                return; // Exit method
            }

            // Format the URL
            String url = String.format("https://api.dictionaryapi.dev/api/v2/entries/en/%s", input);

            // Create a request using Volley
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            // Process the response
                            List<Definition> definitions = new ArrayList<>();
                            try {
                                for (int i = 0; i < response.length(); ++i) {
                                    JSONObject wordObject = response.getJSONObject(i);
                                    JSONArray meaningsArray = wordObject.getJSONArray("meanings");
                                    for (int j = 0; j < meaningsArray.length(); j++) {
                                        JSONObject meaningObject = meaningsArray.getJSONObject(j);
                                        String partOfSpeech = meaningObject.getString("partOfSpeech");
                                        JSONArray defArray = meaningObject.getJSONArray("definitions");
                                        for (int k = 0; k < defArray.length(); k++) {
                                            JSONObject defObject = defArray.getJSONObject(k);
                                            String definition = defObject.getString("definition");
                                            definitions.add(new Definition(input, definition, partOfSpeech));
                                        }
                                    }
                                }
                                activity.setFragment(new DefinitionsListFragment(input, definitions), true);
                            } catch (Exception e) {
                                // Show error dialog if an exception occurs
                                new AlertDialog.Builder(getContext())
                                        .setTitle("Error")
                                        .setMessage(e.getLocalizedMessage())
                                        .setNeutralButton("Ok", (dialog, i) -> dialog.dismiss())
                                        .show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Show error dialog if Volley encounters an error
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Error")
                                    .setMessage(error.getLocalizedMessage())
                                    .setNeutralButton("Ok", (dialog, i) -> dialog.dismiss())
                                    .show();
                        }
                    }
            );

            // Add the request to the RequestQueue
            requestQueue.add(jsonArrayRequest);
        });

        binding.savedButton.setOnClickListener(view -> {
            final DefinitionDao dao = this.activity.getDefinitionDao();
            AsyncTask.execute(() -> {
                List<String> words = dao.getAllWords();
                List<Word> definitions = new ArrayList<>();
                for (String w : words) {
                    final List<Definition> newDefinition = dao.getDefinitionsForWord(w);
                    final Word word = new Word(w);
                    word.setDefinitions(newDefinition);
                    definitions.add(word);
                }
                activity.setFragment(new SavedWordsFragment(definitions), true);
            });
        });

        return binding.getRoot();
    }



}
