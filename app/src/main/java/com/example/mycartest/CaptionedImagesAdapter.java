package com.example.mycartest;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CaptionedImagesAdapter extends
        RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {
    //для этого мы расширяем класс RecyclerView.Adapter и переопределяем его различные методы

    private final String[] captions; //переменные для хнанения строк и id
    private Listener listener; //добавим объект listener
    private final String[] mileage;
    private final String[] nxt_mileage;

    //реализуем интерфейс
    public interface Listener{
        void onClick(int position);
    }

    //сообщает адаптеру, какие представления должны использоваться для элементов данных
    public static class ViewHolder extends RecyclerView.ViewHolder {

        //указываем, что данный класс использует карточное представление
        private final CardView cardView;
        public ViewHolder(CardView v) {
            super(v); //суперкласс включает метаданные, необходимые для правильной работы адаптера
            cardView = v;
        }
    }

    //создаем конструктор для передачи данных
    public CaptionedImagesAdapter(String[] captions, String[] mileage, String[] nxt_mileage){
        this.captions = captions;
        this.mileage = mileage;
        this.nxt_mileage = nxt_mileage;
    }

    //метод вызывается, когда  RecyclerView требуется создать ViewHolder
    @NonNull
    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType){
    //Код создания экземпляра ViewHolder
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image, parent, false);
    //Получает объект LayoutInflater,к. преобразует макет в CardView
        return new ViewHolder(cv);
    }

    //возвращает к-во элементов данных
    @Override
    public int getItemCount(){
        return captions.length; //длина массива равна к-ву элементов данных в RecyclerView
    }
    // Активности и фрагменты используют этот метод для регистрации себя в качестве слушателя
    public void setListener (Listener listener){
        this.listener = listener;
    }

    //RecyclerView вызывает этот метод, когда потребуется использовать (или повторно использовать)
    // ViewHolder для новой порции данных.
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position){
        //переменную position необходимо снабдить модификатором final,
        //так как она используется во внутреннем классе
        CardView cardView = holder.cardView;

        TextView textView = cardView.findViewById(R.id.info_text);
        textView.setText(captions[position]);
        TextView textMileage = cardView.findViewById(R.id.mileage_text);
        textMileage.setText(mileage[position]);
        TextView textNxtMileage = cardView.findViewById(R.id.next_mileage_text);
        textNxtMileage.setText(nxt_mileage[position]);

        //Интерфейс добавляетс к CardView.
        //При щелчке на CardView вызвать метод onClick() интерфейса Listener
        cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(position);
            }
        });
    }

}
