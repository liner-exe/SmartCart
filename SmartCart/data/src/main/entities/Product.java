package entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Shop.class,
                parentColumns = "id",
                childColumns = "shop_id"
        )
)
public class Product {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    public int price;

    public int quantity;
}
