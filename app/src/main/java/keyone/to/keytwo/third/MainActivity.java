package keyone.to.keytwo.third;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String CURRENT_NOTE = "CurrentNote";
    public Note currentNote;

    //здесь оставляем все как есть
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            // Восстановление текущей позиции.
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            // Если воccтановить не удалось, то сделаем объект с первым индексом
            currentNote = new Note(0);
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            initView();
        }
    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    private void initView() {
        initButtonNoteList();
        initButtonNoteBody();
    }
    private void initButtonNoteList() {
        Button buttonNoteList = findViewById(R.id.buttonNoteList);
        buttonNoteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new NotesListFragment());
            }
        });
    }

    private void initButtonNoteBody() {
        Button buttonNoteBody = findViewById(R.id.buttonNoteBody);
        buttonNoteBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentNote!=null){ // если у нас уже выбрана заметка, и нам есть что показать
                    addFragment(NoteDescriptionFragment.newInstance(currentNote));
                }else{
                    Toast.makeText(getApplicationContext(),"Не выбрана ни одна заметка",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addFragment(Fragment fragment){
        //Получить менеджер фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //поместить нужный фрагмент в контейнер
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        //применить изменения
        fragmentTransaction.commit();
    }
}