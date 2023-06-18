package com.example.blink.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.blink.database.daos.CartItemDao;
import com.example.blink.database.daos.CategoryDao;
import com.example.blink.database.daos.OrderDao;
import com.example.blink.database.daos.ProductDao;
import com.example.blink.database.daos.SupplierDao;
import com.example.blink.database.entities.CartItem;
import com.example.blink.database.entities.Category;
import com.example.blink.database.entities.Order;
import com.example.blink.database.entities.Product;
import com.example.blink.database.entities.Supplier;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {Product.class, Category.class, Supplier.class, CartItem.class, Order.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
    public abstract CategoryDao categoryDao();
    public abstract SupplierDao supplierDao();
    public abstract CartItemDao cartItemDao();
    public abstract OrderDao orderDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "database-name")
                .allowMainThreadQueries()
                .addCallback(new RoomDatabase.Callback() {
                    public void onCreate(SupportSQLiteDatabase db) {
                        super.onCreate(db);

                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                AppDatabase appDatabase = AppDatabase.getInstance(context);
                                CategoryDao categoryDao = appDatabase.categoryDao();
                                SupplierDao supplierDao = appDatabase.supplierDao();
                                ProductDao productDao = appDatabase.productDao();

                                List<Category> categories = Arrays.asList(
                                        new Category("Brot & Brötchen"),
                                        new Category("Käse"),
                                        new Category("Kaffee"),
                                        new Category("Tee"),
                                        new Category("Nudeln & Reis"),
                                        new Category("Ponies")
                                );

                                List<Supplier> suppliers = Arrays.asList(
                                        new Supplier("Aldi Süd"),
                                        new Supplier("Aldi Nord"),
                                        new Supplier("Lidl"),
                                        new Supplier("Rewe"),
                                        new Supplier("Edeka"),
                                        new Supplier("Penny"),
                                        new Supplier("Netto")
                                );

                                List<Product> products = Arrays.asList(
                                        new Product("Rotes Pony", 20000, "Ponies", "Rewe"),
                                        new Product("Langhaariges Pony", 25000, "Ponies", "Rewe"),
                                        new Product("Pferdefleischlasagne", 6.99, "Ponies", "Rewe"),
                                        new Product("Pony mit Sitzheizung", 40000, "Ponies", "Rewe"),
                                        new Product("Milchbrötchen", 0.27, "Brot & Brötchen", "Aldi Süd"),
                                        new Product("Milchbrötchen", 0.27, "Brot & Brötchen", "Aldi Nord"),
                                        new Product("Milchbrötchen", 0.27, "Brot & Brötchen", "Lidl"),
                                        new Product("Milchbrötchen", 0.27, "Brot & Brötchen", "Rewe"),
                                        new Product("Milchbrötchen", 0.27, "Brot & Brötchen", "Edeka"),
                                        new Product("Milchbrötchen", 0.31, "Brot & Brötchen", "Penny"),
                                        new Product("Milchbrötchen", 0.27, "Brot & Brötchen", "Netto"),
                                        new Product("Roggenmischbrot", 0.18, "Brot & Brötchen", "Netto"),
                                        new Product("Croissants", 0.62, "Brot & Brötchen", "Aldi Süd"),
                                        new Product("Croissants", 0.68, "Brot & Brötchen", "Aldi Nord"),
                                        new Product("Croissants", 0.62, "Brot & Brötchen", "Lidl"),
                                        new Product("Croissants", 0.70, "Brot & Brötchen", "Rewe"),
                                        new Product("Croissants", 0.62, "Brot & Brötchen", "Edeka"),
                                        new Product("Croissants", 0.62, "Brot & Brötchen", "Penny"),
                                        new Product("Croissants", 0.65, "Brot & Brötchen", "Netto"),
                                        new Product("Dreikorntoast", 0.11, "Brot & Brötchen", "Aldi Süd"),
                                        new Product("Dreikorntoast", 0.11, "Brot & Brötchen", "Aldi Nord"),
                                        new Product("Dreikorntoast", 0.14, "Brot & Brötchen", "Lidl"),
                                        new Product("Dreikorntoast", 0.14, "Brot & Brötchen", "Rewe"),
                                        new Product("Dreikorntoast", 0.14, "Brot & Brötchen", "Edeka"),
                                        new Product("Dreikorntoast", 0.12, "Brot & Brötchen", "Penny"),
                                        new Product("Dreikorntoast", 0.12, "Brot & Brötchen", "Netto"),
                                        new Product("Toastbrötchen", 0.30, "Brot & Brötchen", "Edeka"),
                                        new Product("Toastbrötchen", 0.36, "Brot & Brötchen", "Penny"),
                                        new Product("Toastbrötchen", 0.27, "Brot & Brötchen", "Netto"),
                                        new Product("Sandwich-Toast", 0.20, "Brot & Brötchen", "Aldi Süd"),
                                        new Product("Sandwich-Toast", 0.11, "Brot & Brötchen", "Aldi Nord"),
                                        new Product("Sandwich-Toast", 0.13, "Brot & Brötchen", "Lidl"),
                                        new Product("Sandwich-Toast", 0.13, "Brot & Brötchen", "Rewe"),
                                        new Product("Sandwich-Toast", 0.11, "Brot & Brötchen", "Edeka"),
                                        new Product("Sandwich-Toast", 0.13, "Brot & Brötchen", "Penny"),
                                        new Product("Sandwich-Toast", 0.13, "Brot & Brötchen", "Netto"),
                                        new Product("Knäckebrot", 0.52, "Brot & Brötchen", "Aldi Süd"),
                                        new Product("Knäckebrot", 0.24, "Brot & Brötchen", "Aldi Nord"),
                                        new Product("Knäckebrot", 0.28, "Brot & Brötchen", "Lidl"),
                                        new Product("Knäckebrot", 0.24, "Brot & Brötchen", "Penny"),
                                        new Product("Knäckebrot", 0.24, "Brot & Brötchen", "Netto"),
                                        new Product("Buttertoast", 0.24, "Brot & Brötchen", "Aldi Süd"),
                                        new Product("Buttertoast", 0.12, "Brot & Brötchen", "Aldi Nord"),
                                        new Product("Buttertoast", 0.16, "Brot & Brötchen", "Lidl"),
                                        new Product("Buttertoast", 0.16, "Brot & Brötchen", "Rewe"),
                                        new Product("Buttertoast", 0.14, "Brot & Brötchen", "Edeka"),
                                        new Product("Buttertoast", 0.14, "Brot & Brötchen", "Penny"),
                                        new Product("Buttertoast", 0.14, "Brot & Brötchen", "Netto"),
                                        new Product("Rosinenschnitten", 0.35, "Brot & Brötchen", "Aldi Süd"),
                                        new Product("Rosinenschnitten", 0.25, "Brot & Brötchen", "Aldi Nord"),
                                        new Product("Rosinenschnitten", 0.25, "Brot & Brötchen", "Penny"),
                                        new Product("Ciabatta", 1.31, "Brot & Brötchen", "Aldi Süd"),
                                        new Product("Ciabatta", 1.31, "Brot & Brötchen", "Aldi Nord"),
                                        new Product("Ciabatta", 1.31, "Brot & Brötchen", "Lidl"),
                                        new Product("Ciabatta", 1.31, "Brot & Brötchen", "Rewe"),
                                        new Product("Baguette", 0.27, "Brot & Brötchen", "Aldi Süd"),
                                        new Product("Baguette", 0.27, "Brot & Brötchen", "Aldi Nord"),
                                        new Product("Baguette", 0.27, "Brot & Brötchen", "Lidl"),
                                        new Product("Baguette", 0.27, "Brot & Brötchen", "Rewe"),
                                        new Product("Finn-Brötchen", 0.38, "Brot & Brötchen", "Aldi Süd"),
                                        new Product("Finn-Brötchen", 0.38, "Brot & Brötchen", "Aldi Nord"),
                                        new Product("Finn-Brötchen", 0.38, "Brot & Brötchen", "Edeka"),
                                        new Product("Finn-Brötchen", 0.38, "Brot & Brötchen", "Penny"),
                                        new Product("Finn-Brötchen", 0.34, "Brot & Brötchen", "Netto"),
                                        new Product("Schokobrötchen", 0.41, "Brot & Brötchen", "Aldi Süd"),
                                        new Product("Schokobrötchen", 0.41, "Brot & Brötchen", "Aldi Nord"),
                                        new Product("Schokobrötchen", 0.41, "Brot & Brötchen", "Lidl"),
                                        new Product("Schokobrötchen", 0.41, "Brot & Brötchen", "Rewe"),
                                        new Product("Schokobrötchen", 0.41, "Brot & Brötchen", "Edeka"),
                                        new Product("Schokobrötchen", 0.41, "Brot & Brötchen", "Penny"),
                                        new Product("Schokobrötchen", 0.41, "Brot & Brötchen", "Netto"),
                                        new Product("Toast, Vollkorn", 0.12, "Brot & Brötchen", "Lidl"),
                                        new Product("Toast, Vollkorn", 0.12, "Brot & Brötchen", "Rewe"),
                                        new Product("Toast, Vollkorn", 0.14, "Brot & Brötchen", "Edeka"),
                                        new Product("Toast, Vollkorn", 0.14, "Brot & Brötchen", "Penny"),
                                        new Product("Baguette Brötchen", 0.23, "Brot & Brötchen", "Aldi Süd"),
                                        new Product("Baguette Brötchen", 0.13, "Brot & Brötchen", "Aldi Nord"),
                                        new Product("Baguette Brötchen", 0.15, "Brot & Brötchen", "Lidl"),
                                        new Product("Baguette Brötchen", 0.15, "Brot & Brötchen", "Rewe"),
                                        new Product("Baguette Brötchen", 0.13, "Brot & Brötchen", "Edeka"),
                                        new Product("Hamburger Brötchen", 0.69, "Brot & Brötchen", "Aldi Süd"),
                                        new Product("Hamburger Brötchen", 0.65, "Brot & Brötchen", "Aldi Nord"),
                                        new Product("Hamburger Brötchen", 0.53, "Brot & Brötchen", "Lidl"),
                                        new Product("Hamburger Brötchen", 0.53, "Brot & Brötchen", "Rewe"),
                                        new Product("Hamburger Brötchen", 0.63, "Brot & Brötchen", "Edeka"),
                                        new Product("Hamburger Brötchen", 0.53, "Brot & Brötchen", "Penny"),
                                        new Product("Bauernbrot", 0.20, "Brot & Brötchen", "Aldi Süd"),
                                        new Product("Bauernbrot", 0.12, "Brot & Brötchen", "Aldi Nord"),
                                        new Product("Bauernbrot", 0.14, "Brot & Brötchen", "Rewe"),
                                        new Product("Bauernbrot", 0.13, "Brot & Brötchen", "Edeka"),
                                        new Product("Bauernbrot", 0.13, "Brot & Brötchen", "Netto"),
                                        new Product("Fladenbrot", 0.20, "Brot & Brötchen", "Aldi Süd"),
                                        new Product("Ein Euro", 0.90, "Brot & Brötchen", "Aldi Süd"),
                                        new Product("Fladenbrot", 0.20, "Brot & Brötchen", "Aldi Nord"),
                                        new Product("Fladenbrot", 0.20, "Brot & Brötchen", "Lidl"),
                                        new Product("Fladenbrot", 0.20, "Brot & Brötchen", "Rewe"),
                                        new Product("Fladenbrot", 0.20, "Brot & Brötchen", "Edeka"),
                                        new Product("Fladenbrot", 0.20, "Brot & Brötchen", "Penny"),
                                        new Product("Fladenbrot", 0.20, "Brot & Brötchen", "Netto"),
                                        new Product("Krustenbrot", 0.12, "Brot & Brötchen", "Aldi Nord"),
                                        new Product("Krustenbrot", 0.20, "Brot & Brötchen", "Lidl"),
                                        new Product("Krustenbrot", 0.20, "Brot & Brötchen", "Rewe"),
                                        new Product("Krustenbrot", 0.22, "Brot & Brötchen", "Edeka"),
                                        new Product("Krustenbrot", 0.22, "Brot & Brötchen", "Penny"),
                                        new Product("Käseaufschnitt", 1.56, "Käse", "Aldi Nord"),
                                        new Product("Käseaufschnitt", 2.60, "Käse", "Lidl"),
                                        new Product("Käseaufschnitt", 2.56, "Käse", "Rewe"),
                                        new Product("Käseaufschnitt", 2.26, "Käse", "Edeka"),
                                        new Product("Mozzarella", 0.79, "Käse", "Aldi Süd"),
                                        new Product("Mozzarella", 0.52, "Käse", "Aldi Nord"),
                                        new Product("Mozzarella", 0.30, "Käse", "Lidl"),
                                        new Product("Mozzarella", 0.49, "Käse", "Rewe"),
                                        new Product("Mozzarella", 0.52, "Käse", "Edeka"),
                                        new Product("Mozzarella", 0.75, "Käse", "Penny"),
                                        new Product("Mozzarella", 0.55, "Käse", "Netto"),
                                        new Product("Gouda, in Scheiben", 1.00, "Käse", "Aldi Süd"),
                                        new Product("Gouda, in Scheiben", 0.83, "Käse", "Aldi Nord"),
                                        new Product("Gouda, in Scheiben", 0.90, "Käse", "Lidl"),
                                        new Product("Gouda, in Scheiben", 1.16, "Käse", "Rewe"),
                                        new Product("Gouda, in Scheiben", 1.75, "Käse", "Edeka"),
                                        new Product("Gouda, in Scheiben", 1.62, "Käse", "Penny"),
                                        new Product("Frischkäse, Doppelrahmstufe", 0.76, "Käse", "Rewe"),
                                        new Product("Frischkäse, Doppelrahmstufe", 0.80, "Käse", "Edeka"),
                                        new Product("Österreichischer Bergkäse", 4.59, "Käse", "Aldi Süd"),
                                        new Product("Österreichischer Bergkäse", 4.79, "Käse", "Aldi Nord"),
                                        new Product("Feta", 1.00, "Käse", "Aldi Süd"),
                                        new Product("Feta", 1.44, "Käse", "Aldi Nord"),
                                        new Product("Feta", 1.46, "Käse", "Rewe"),
                                        new Product("Feta", 1.49, "Käse", "Edeka"),
                                        new Product("Brie", 1.49, "Käse", "Aldi Nord"),
                                        new Product("Brie", 1.60, "Käse", "Rewe"),
                                        new Product("Harzer", 0.89, "Käse", "Aldi Süd"),
                                        new Product("Harzer", 0.89, "Käse", "Aldi Nord"),
                                        new Product("Harzer", 0.65, "Käse", "Rewe"),
                                        new Product("Harzer", 0.75, "Käse", "Edeka"),
                                        new Product("Harzer", 0.65, "Käse", "Penny"),
                                        new Product("Hüttenkäse", 0.89, "Käse", "Aldi Süd"),
                                        new Product("Hüttenkäse", 0.84, "Käse", "Aldi Nord"),
                                        new Product("Hüttenkäse", 0.74, "Käse", "Rewe"),
                                        new Product("Hüttenkäse", 0.80, "Käse", "Edeka"),
                                        new Product("Hüttenkäse", 0.84, "Käse", "Penny"),
                                        new Product("Camembert", 1.55, "Käse", "Aldi Nord"),
                                        new Product("Camembert", 1.55, "Käse", "Edeka"),
                                        new Product("Schmelzkäsescheiben", 0.72, "Käse", "Aldi Süd"),
                                        new Product("Schmelzkäsescheiben", 0.40, "Käse", "Aldi Nord"),
                                        new Product("Schmelzkäsescheiben", 0.46, "Käse", "Lidl"),
                                        new Product("Schmelzkäsescheiben", 0.40, "Käse", "Rewe"),
                                        new Product("Schmelzkäsescheiben", 0.40, "Käse", "Edeka"),
                                        new Product("Schmelzkäsescheiben", 0.40, "Käse", "Penny"),
                                        new Product("Schmelzkäsescheiben", 0.40, "Käse", "Netto"),
                                        new Product("Frischkäsefass", 1.90, "Käse", "Aldi Süd"),
                                        new Product("Frischkäsefass", 1.80, "Käse", "Aldi Nord"),
                                        new Product("Havarti in Scheiben", 1.25, "Käse", "Aldi Süd"),
                                        new Product("Havarti in Scheiben", 0.40, "Käse", "Aldi Nord"),
                                        new Product("Mozzarella mini", 1.11, "Käse", "Aldi Süd"),
                                        new Product("Mozzarella mini", 1.06, "Käse", "Aldi Nord"),
                                        new Product("Mozzarella mini", 0.71, "Käse", "Lidl"),
                                        new Product("Mozzarella mini", 0.84, "Käse", "Penny"),
                                        new Product("Gouda, mittel am Stück", 4.99, "Käse", "Aldi Süd"),
                                        new Product("Gouda, mittel am Stück", 2.64, "Käse", "Lidl"),
                                        new Product("Kakaohaltiges Getränkepulver", 3.7, "Kaffee", "Aldi Süd"),
                                        new Product("Kakaohaltiges Getränkepulver", 2.9, "Kaffee", "Aldi Nord"),
                                        new Product("Kakaohaltiges Getränkepulver", 2.7, "Kaffee", "Lidl"),
                                        new Product("Kakaohaltiges Getränkepulver", 2.9, "Kaffee", "Rewe"),
                                        new Product("Kakaohaltiges Getränkepulver", 2.9, "Kaffee", "Edeka"),
                                        new Product("Kakaohaltiges Getränkepulver", 2.7, "Kaffee", "Penny"),
                                        new Product("Kakaohaltiges Getränkepulver", 2.9, "Kaffee", "Netto"),
                                        new Product("Cappuccino", 2.74, "Kaffee", "Aldi Nord"),
                                        new Product("Ceylon-Assam-Tee", 0.75, "Tee", "Aldi Nord"),
                                        new Product("Ceylon-Assam-Tee", 0.75, "Tee", "Lidl"),
                                        new Product("Ceylon-Assam-Tee", 0.75, "Tee", "Rewe"),
                                        new Product("Ceylon-Assam-Tee", 0.75, "Tee", "Edeka"),
                                        new Product("Ceylon-Assam-Tee", 0.75, "Tee", "Penny"),
                                        new Product("Ceylon-Assam-Tee", 0.75, "Tee", "Netto"),
                                        new Product("Waldfrucht", 4.40, "Tee", "Aldi Süd"),
                                        new Product("Waldfrucht", 2.25, "Tee", "Aldi Nord"),
                                        new Product("Waldfrucht", 2.40, "Tee", "Lidl"),
                                        new Product("Waldfrucht", 2.30, "Tee", "Rewe"),
                                        new Product("Waldfrucht", 3.40, "Tee", "Edeka"),
                                        new Product("Fusilli", 2.0, "Nudeln & Reis", "Aldi Süd"),
                                        new Product("Fusilli", 1.8, "Nudeln & Reis", "Aldi Nord"),
                                        new Product("Fusilli", 1.9, "Nudeln & Reis", "Lidl"),
                                        new Product("Fusilli", 1.6, "Nudeln & Reis", "Edeka"),
                                        new Product("Fusilli", 1.75, "Nudeln & Reis", "Netto"),
                                        new Product("Spaghetti", 3.0, "Nudeln & Reis", "Aldi Süd"),
                                        new Product("Spaghetti", 1.0, "Nudeln & Reis", "Lidl"),
                                        new Product("Spaghetti", 0.8, "Nudeln & Reis", "Rewe"),
                                        new Product("Spaghetti", 1.2, "Nudeln & Reis", "Edeka"),
                                        new Product("Spaghetti", 0.9, "Nudeln & Reis", "Penny"),
                                        new Product("Spaghetti", 1.2, "Nudeln & Reis", "Netto"),
                                        new Product("Bandnudeln", 2.2, "Nudeln & Reis", "Aldi Nord"),
                                        new Product("Bandnudeln", 1.4, "Nudeln & Reis", "Lidl"),
                                        new Product("Bandnudeln", 2.0, "Nudeln & Reis", "Rewe"),
                                        new Product("Bandnudeln", 1.2, "Nudeln & Reis", "Penny"),
                                        new Product("Bandnudeln", 2.8, "Nudeln & Reis", "Netto"),
                                        new Product("Reis Parboiled", 1.5, "Nudeln & Reis", "Aldi Süd"),
                                        new Product("Reis Parboiled", 1.3, "Nudeln & Reis", "Aldi Nord"),
                                        new Product("Reis Parboiled", 1.0, "Nudeln & Reis", "Lidl"),
                                        new Product("Reis Parboiled", 1.0, "Nudeln & Reis", "Rewe"),
                                        new Product("Reis Parboiled", 1.0, "Nudeln & Reis", "Edeka"),
                                        new Product("Reis", 0.9, "Nudeln & Reis", "Aldi Süd"),
                                        new Product("Reis", 0.88, "Nudeln & Reis", "Aldi Nord"),
                                        new Product("Reis", 1.0, "Nudeln & Reis", "Lidl"),
                                        new Product("Reis", 1.05, "Nudeln & Reis", "Rewe"),
                                        new Product("Reis", 0.95, "Nudeln & Reis", "Edeka"),
                                        new Product("Reis", 0.95, "Nudeln & Reis", "Penny"),
                                        new Product("Reis", 0.98, "Nudeln & Reis", "Netto"),
                                        new Product("Milchreis", 1.4, "Nudeln & Reis", "Aldi Süd"),
                                        new Product("Milchreis", 1.0, "Nudeln & Reis", "Aldi Nord"),
                                        new Product("Kräuter-Baguette", 0.28, "Brot & Brötchen", "Aldi Süd"),
                                        new Product("Kräuter-Baguette", 0.28, "Brot & Brötchen", "Aldi Nord"),
                                        new Product("Kräuter-Baguette", 0.34, "Brot & Brötchen", "Lidl"),
                                        new Product("Kräuter-Baguette", 0.34, "Brot & Brötchen", "Rewe"),
                                        new Product("Kräuter-Baguette", 0.34, "Brot & Brötchen", "Edeka"),
                                        new Product("Milchreis", 1.2, "Nudeln & Reis", "Rewe")
                                );

                                categoryDao.Insert(categories);
                                supplierDao.Insert(suppliers);
                                productDao.Insert(products);
                            }
                        });
                    }
                }).build();
        }
        return instance;
    }
}
