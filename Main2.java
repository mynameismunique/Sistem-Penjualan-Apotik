package com.apotik;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {

    private static List<PenjualanObat> listObatPenjualan = new ArrayList<>();
    private static LinkedList<String> riwayatPenjualan = new LinkedList<>();
    private static Queue<PenjualanObat> antrianPenjualan = new LinkedList<>();
    private static int nextIdPelanggan = 1;

    // Pemetaan antara nama obat dan kode obat
    private static HashMap<String, String> namaToKodeMap = new HashMap<>();

    private static JFrame frame = new JFrame("Sistem Penjualan Apotik");

    private static JLabel labelIdPelanggan = new JLabel("ID Pelanggan:");
    private static JTextField inputIdPelanggan = new JTextField();

    private static JLabel labelNamaPelanggan = new JLabel("Nama Pelanggan:");
    private static JTextField inputNamaPelanggan = new JTextField();

    private static JLabel labelKodeObat = new JLabel("Kode Obat:");
    private static JTextField inputKodeObat = new JTextField();

    private static JLabel labelNamaObat = new JLabel("Nama Obat:");
    private static JComboBox<String> comboNamaObat = new JComboBox<>();

    private static JLabel labelHarga = new JLabel("Harga:");
    private static JTextField inputHarga = new JTextField();

    private static JLabel labelJumlah = new JLabel("Jumlah:");
    private static JTextField inputJumlah = new JTextField();

    private static JButton btnJual = new JButton("Jual");
    private static JButton btnAntri = new JButton("Antri");

    private static JLabel labelTotal = new JLabel("Total:");
    private static JTextField inputTotal = new JTextField();

    private static JLabel labelStokObat = new JLabel("Stok Obat:");
    private static JTextArea areaStokObat = new JTextArea();

    public static void main(String[] args) {
        listObatPenjualan.add(new PenjualanObat("Paracetamol", "PCT001", 50, 5000));
        listObatPenjualan.add(new PenjualanObat("Amoxicillin", "AMX001", 30, 10000));
        listObatPenjualan.add(new PenjualanObat("Aspirin", "ASP001", 40, 7000));
        listObatPenjualan.add(new PenjualanObat("Omeprazole", "OMP001", 25, 12000));
        listObatPenjualan.add(new PenjualanObat("Loratadine", "LRT001", 35, 8000));

        // Mengisi pemetaan nama obat ke kode obat
        for (PenjualanObat obat : listObatPenjualan) {
            namaToKodeMap.put(obat.namaObat, obat.kodeObat);
        }

        frame.setPreferredSize(new Dimension(800, 600));
        frame.setLayout(new GridLayout(8, 2, 10, 10));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        labelIdPelanggan.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.add(labelIdPelanggan);
        frame.add(inputIdPelanggan);

        labelNamaPelanggan.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.add(labelNamaPelanggan);
        frame.add(inputNamaPelanggan);

        labelKodeObat.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.add(labelKodeObat);
        frame.add(inputKodeObat);

        labelNamaObat.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.add(labelNamaObat);
        frame.add(comboNamaObat);

        labelHarga.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.add(labelHarga);
        frame.add(inputHarga);

        labelJumlah.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.add(labelJumlah);
        frame.add(inputJumlah);

        btnJual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jualObat();
                updateStokObat();
            }
        });
        frame.add(btnJual);

        btnAntri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                antriPenjualan();
                updateStokObat();
            }
        });
        frame.add(btnAntri);

        labelTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.add(labelTotal);
        frame.add(inputTotal);

        labelStokObat.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.add(labelStokObat);
        areaStokObat.setEditable(false);
        frame.add(new JScrollPane(areaStokObat));

        // Mengisi combo box dengan nama obat
        for (PenjualanObat obat : listObatPenjualan) {
            comboNamaObat.addItem(obat.namaObat);
        }

        // Menambahkan action listener pada combo box untuk mengisi otomatis harga dan kode obat
        comboNamaObat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedNamaObat = comboNamaObat.getSelectedItem().toString();
                inputKodeObat.setText(namaToKodeMap.get(selectedNamaObat));
                inputHarga.setText(String.valueOf(getHargaByNamaObat(selectedNamaObat)));
            }
        });

        frame.pack();
        frame.setVisible(true);

        updateStokObat();
    }

    private static void jualObat() {
        int idPelanggan = Integer.parseInt(inputIdPelanggan.getText());
        String namaPelanggan = inputNamaPelanggan.getText();
        String kodeObat = inputKodeObat.getText();
        String namaObat = comboNamaObat.getSelectedItem().toString();
        int harga = Integer.parseInt(inputHarga.getText());
        int jumlah = Integer.parseInt(inputJumlah.getText());

        int total = harga * jumlah;
        inputTotal.setText(String.valueOf(total));

        PenjualanObat obatPenjualan = new PenjualanObat(namaObat, kodeObat, harga, jumlah);
        listObatPenjualan.add(obatPenjualan);

        riwayatPenjualan.add("Penjualan - ID: " + idPelanggan + ", Nama: " + namaPelanggan +
                ", Obat: " + obatPenjualan.namaObat + ", Total: " + total);

        for (PenjualanObat obat : listObatPenjualan) {
            if (obat.namaObat.equalsIgnoreCase(namaObat)) {
                obat.stok -= jumlah;
                break;
            }
        }

        inputIdPelanggan.setText("");
        inputNamaPelanggan.setText("");
        inputKodeObat.setText("");
        comboNamaObat.setSelectedIndex(0);
        inputHarga.setText("");
        inputJumlah.setText("");
    }

    private static void antriPenjualan() {
        int idPelanggan = Integer.parseInt(inputIdPelanggan.getText());
        String namaPelanggan = inputNamaPelanggan.getText();
        String kodeObat = inputKodeObat.getText();
        String namaObat = comboNamaObat.getSelectedItem().toString();
        int harga = Integer.parseInt(inputHarga.getText());
        int jumlah = Integer.parseInt(inputJumlah.getText());

        int total = harga * jumlah;
        inputTotal.setText(String.valueOf(total));

        PenjualanObat obatPenjualan = new PenjualanObat(namaObat, kodeObat, harga, jumlah);
        antrianPenjualan.add(obatPenjualan);

        for (PenjualanObat obat : listObatPenjualan) {
            if (obat.namaObat.equalsIgnoreCase(namaObat)) {
                obat.stok -= jumlah;
                break;
            }
        }

        inputIdPelanggan.setText("");
        inputNamaPelanggan.setText("");
        inputKodeObat.setText("");
        comboNamaObat.setSelectedIndex(0);
        inputHarga.setText("");
        inputJumlah.setText("");
    }

    private static void updateStokObat() {
        StringBuilder stokInfo = new StringBuilder("Stok Obat:\n");
        for (PenjualanObat obat : listObatPenjualan) {
            stokInfo.append(obat.namaObat).append(": ").append(obat.stok).append("\n");
        }
        areaStokObat.setText(stokInfo.toString());
    }

    // Mendapatkan harga berdasarkan nama obat
    private static int getHargaByNamaObat(String namaObat) {
        for (PenjualanObat obat : listObatPenjualan) {
            if (obat.namaObat.equalsIgnoreCase(namaObat)) {
                return obat.harga;
            }
        }
        return 0; // Jika tidak ditemukan, mengembalikan nilai 0
    }

    private static class PenjualanObat {
        String namaObat;
        String kodeObat;
        int stok;
        int harga;

        // Konstruktor untuk inisialisasi data obat
        public PenjualanObat(String namaObat, String kodeObat, int stok, int harga) {
            this.namaObat = namaObat;
            this.kodeObat = kodeObat;
            this.stok = stok;
            this.harga = harga;
        }
    }
}
