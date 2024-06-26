package it.saimao.tmkkeyboard.activities;

import static it.saimao.tmkkeyboard.utils.Constants.THEME_LIST;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.function.Consumer;

import it.saimao.tmkkeyboard.R;
import it.saimao.tmkkeyboard.adapters.OnThemeClickListener;
import it.saimao.tmkkeyboard.adapters.Theme;
import it.saimao.tmkkeyboard.adapters.ThemeAdapter;
import it.saimao.tmkkeyboard.databinding.ActivityChooseThemeBinding;
import it.saimao.tmkkeyboard.utils.PrefManager;
import it.saimao.tmkkeyboard.utils.Utils;

public class ChooseThemeActivity extends AppCompatActivity {

    private ActivityChooseThemeBinding binding;
    private ThemeAdapter themeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseThemeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUi();
    }

    private void initUi() {
        themeAdapter = new ThemeAdapter(theme -> {
            var selected = THEME_LIST.indexOf(theme);
            PrefManager.setKeyboardTheme(this, selected);
            refreshThemes();
            Utils.setThemeChanged(true);
        });
        binding.rvThemes.setAdapter(themeAdapter);
        binding.rvThemes.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        refreshThemes();

    }

    private void refreshThemes() {
        ArrayList<Theme> themes = new ArrayList<>(THEME_LIST);
        themes.forEach(theme -> {
            System.out.println(theme.getName() + " : " + theme.isSelected());
        });
        int selected = PrefManager.getKeyboardTheme(this);
        Theme selectedTheme = themes.get(selected);
        Theme newTheme = new Theme(selectedTheme);
        Log.d("TMK Group", "" + (selectedTheme == newTheme));
        newTheme.setSelected(true);
        themes.set(selected, newTheme);
        themeAdapter.setThemes(themes);
    }

}