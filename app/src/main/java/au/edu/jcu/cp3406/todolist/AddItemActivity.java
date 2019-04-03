package au.edu.jcu.cp3406.todolist;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class AddItemActivity extends AppCompatActivity {
    EditText editText;
    SharedPreferences dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        dataSource = getSharedPreferences("todo items", Context.MODE_PRIVATE);
        editText = findViewById(R.id.edit_text);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(this, "No item name given", Toast.LENGTH_SHORT).show();
                    return true;
                }

                Set<String> items = dataSource.getStringSet("items", new HashSet<String>());
                assert items != null;
                items.add(editText.getText().toString());
                dataSource.edit().clear().putStringSet("items", items).apply();

                Toast.makeText(this, "Added", Toast.LENGTH_LONG).show();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
