package com.example.adolfo.masterdetail.dummy;

import com.example.adolfo.masterdetail.Jugador;
import com.example.adolfo.masterdetail.JugadorListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 25;

    static {

    }

    public static void rellenarItems(){
        ArrayList<Jugador> array = JugadorListActivity.arrayJugadores;
        ITEMS.clear();
        ITEM_MAP.clear();
        for(int i=0; i<array.size(); i++){
            addItem(new DummyItem(i + "", array.get(i).getNombreJugador(),array.get(i).getNombreEquipo() , array.get(i).getDorsal(),array.get(i).getLink()));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
    /*
    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }
    */
    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String nombreJugador, equipo, link;
        public final int dorsal;

        public DummyItem(String id, String nombreJugador, String equipo, int dorsal, String link) {
            this.id = id;
            this.nombreJugador = nombreJugador;
            this.equipo = equipo;
            this.dorsal = dorsal;
            this.link = link;
        }

        @Override
        public String toString() {
            return nombreJugador;
        }
    }
}
