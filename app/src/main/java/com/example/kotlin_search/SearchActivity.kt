package com.example.kotlin_search

import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.nex3z.flowlayout.FlowLayout


class SearchActivity : AppCompatActivity() {
    lateinit var searchView: SearchView
    lateinit var inflater:LayoutInflater
    lateinit var flowLayout : FlowLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_view)
        searchView = findViewById(R.id.search_view)
        flowLayout = findViewById(R.id.flow_layout)
        inflater = LayoutInflater.from(this)

        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        val cursorAdapter = SimpleCursorAdapter(
            this,
            R.layout.search_item,
            null,
            from,
            to,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )
        val suggestions = listOf(
            "Apple",
            "Apply",
            "Application",
            "Alcohol",
            "Blueberry",
            "Blue",
            "Carrot",
            "Daikon"
        )

        searchView.suggestionsAdapter = cursorAdapter



        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {

                val cursor =
                    MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                query?.let {
                    if(it.contains(" ")) {
                        addTage(it.trim())
                        searchView.setQuery("", false);
                        searchView.clearFocus();
                    }
                    suggestions.forEachIndexed { index, suggestion ->
                        if (suggestion.contains(query, true))
                            cursor.addRow(arrayOf(index, suggestion))
                    }
                }

                cursorAdapter.changeCursor(cursor)
                return true;
            }
        })

        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                Toast.makeText(
                    applicationContext,
                    "Select ${position.toString()}",
                    Toast.LENGTH_LONG
                ).show()
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = searchView.suggestionsAdapter.getItem(position) as Cursor
                val selection =
                    cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                searchView.setQuery(selection, false)

                Toast.makeText(applicationContext, selection, Toast.LENGTH_LONG).show()
                addTage(selection)
                searchView.setQuery("", false)
                searchView.clearFocus()
                return true
            }

        })
    }

    fun addTage(tage: String){
        val view  = inflater.inflate(R.layout.tag_layout, flowLayout, false) as LinearLayout
        val textView = view.getChildAt(0) as TextView
        textView.text = tage
        flowLayout.addView(view)
    }
}