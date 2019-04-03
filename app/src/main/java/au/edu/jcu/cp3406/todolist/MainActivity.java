package au.edu.jcu.cp3406.todolist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    ListView todoList;
    ArrayAdapter<String> adapter;
    SharedPreferences dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoList = findViewById(R.id.to_do_list);
        dataSource = getSharedPreferences("todo items", Context.MODE_PRIVATE);
        adapter = new ArrayAdapter<>(this, R.layout.item);

        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView itemView = (TextView) view;
                adapter.remove(itemView.getText().toString());
            }
        });
        todoList.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.clear();
        Set<String> newItems = dataSource.getStringSet("items", new HashSet<String>());
        assert newItems != null;
        adapter.addAll(newItems);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Set<String> items = new HashSet<>();
        for (int i = 0; i < adapter.getCount(); i++) {
            items.add(adapter.getItem(i));
        }
        dataSource.edit().clear().putStringSet("items", items).apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Intent intent = new Intent(this, AddItemActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
