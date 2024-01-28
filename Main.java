package com.apotik;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    // Deklarasi record untuk data obat
    record Obat(String namaObat, int stok, int harga) {
    }

    // ArrayList untuk menyimpan objek obat
    private static List<Obat> listObat = new ArrayList<>();

    // ArrayList untuk menyimpan riwayat penjualan
    private static List<String> riwayatPenjualan = new ArrayList<>();

    // Container Aplikasi
    private static JFrame form = new JFrame("Sistem Penjualan Apotik");

    // Label dan field untuk input nama obat
    private static JLabel labelNamaObat = new JLabel("Nama Obat:");
    private static JTextField inputNamaObat = new JTextField();

    // Label dan field untuk input jumlah pembelian
    private static JLabel labelJumlahBeli = new JLabel("Jumlah Beli:");
    private static JTextField inputJumlahBeli = new JTextField();

    // Button untuk melakukan penjualan
    private static JButton btnJual = new JButton("Jual");

    // Button untuk menampilkan riwayat penjualan
    private static JButton btnRiwayat = new JButton("Riwayat Penjualan");

    // Label untuk menampilkan hasil penjualan
    private static JLabel labelHasilJual = new JLabel("Hasil Penjualan:");

    // Area teks untuk menampilkan hasil penjualan
    private static JTextArea areaHasilJual = new JTextArea();

    public static void main(String[] args) {
        // Menambahkan objek obat ke dalam listObat
        listObat.add(new Obat("Paracetamol", 50, 5000));
        listObat.add(new Obat("Amoxicillin", 30, 10000));
        listObat.add(new Obat("Aspirin", 40, 7000));
        listObat.add(new Obat("Omeprazole", 25, 12000));
        listObat.add(new Obat("Loratadine", 35, 8000));

        // Mengatur ukuran container
        form.setPreferredSize(new Dimension(800, 600));

        // Mengatur layout menjadi null agar bisa menempatkan komponen secara bebas
        form.setLayout(null);

        // Menambahkan exit program ketika tombol close di klik
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Menempatkan container di tengah layar
        form.setLocationRelativeTo(null);

        // Background container
        form.getContentPane().setBackground(new Color(240, 240, 240));

        // Membuat label untuk judul
        JLabel judul = new JLabel("Sistem Penjualan Apotik");
        judul.setBounds(10, 10, 300, 30);
        judul.setFont(new Font("Arial", Font.BOLD, 20));
        form.add(judul);

        // Membuat label dan field untuk input nama obat
        labelNamaObat.setBounds(10, 50, 80, 20);
        form.add(labelNamaObat);

        inputNamaObat.setBounds(100, 50, 150, 20);
        form.add(inputNamaObat);

        // Membuat label dan field untuk input jumlah pembelian
        labelJumlahBeli.setBounds(10, 80, 80, 20);
        form.add(labelJumlahBeli);

        inputJumlahBeli.setBounds(100, 80, 50, 20);
        form.add(inputJumlahBeli);

        // Membuat button untuk melakukan penjualan
        btnJual.setBounds(10, 110, 80, 30);
        form.add(btnJual);

        // Membuat button untuk menampilkan riwayat penjualan
        btnRiwayat.setBounds(100, 110, 150, 30);
        form.add(btnRiwayat);

        // Membuat label untuk menampilkan hasil penjualan
        labelHasilJual.setBounds(10, 150, 150, 20);
        form.add(labelHasilJual);

        // Membuat area teks untuk menampilkan hasil penjualan
        areaHasilJual.setBounds(10, 180, 400, 300);
        areaHasilJual.setEditable(false);
        form.add(areaHasilJual);

        // Menambahkan action listener pada tombol Jual
        btnJual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jualObat();
            }
        });

        // Menambahkan action listener pada tombol Riwayat Penjualan
        btnRiwayat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tampilkanRiwayat();
            }
        });

        // Menampilkan container
        form.pack();
        form.setVisible(true);
    }

    // Operasi Jual Obat
    private static void jualObat() {
        // Mendapatkan input dari user
        String namaObat = inputNamaObat.getText();
        int jumlahBeli = Integer.parseInt(inputJumlahBeli.getText());

        // Mencari obat yang sesuai dengan namaObat
        Obat obatDibeli = null;
        for (Obat obat : listObat) {
            if (obat.namaObat().equalsIgnoreCase(namaObat)) {
                obatDibeli = obat;
                break;
            }
        }

        // Memproses penjualan
        if (obatDibeli != null) {
            if (obatDibeli.stok() >= jumlahBeli) {
                int totalHarga = obatDibeli.harga() * jumlahBeli;
                areaHasilJual.setText("Obat: " + obatDibeli.namaObat() +
                        "\nJumlah Beli: " + jumlahBeli +
                        "\nTotal Harga: Rp " + totalHarga);
                obatDibeli.stok(obatDibeli.stok() - jumlahBeli);

                // Menambahkan riwayat penjualan
                riwayatPenjualan.add("Penjualan - Obat: " + obatDibeli.namaObat() +
                        ", Jumlah Beli: " + jumlahBeli +
                        ", Total Harga: Rp " + totalHarga);
            } else {
                areaHasilJual.setText("Stok obat tidak mencukupi!");
            }
        } else {
            areaHasilJual.setText("Obat tidak ditemukan!");
        }
    }

    // Operasi Menampilkan Riwayat Penjualan
    private static void tampilkanRiwayat() {
        StringBuilder riwayat = new StringBuilder("Riwayat Penjualan:\n");
        for (String penjualan : riwayatPenjualan) {
            riwayat.append(penjualan).append("\n");
        }
        areaHasilJual.setText(riwayat.toString());
    }
}
