package com.liner_exe.domain.models;

import java.util.Objects;

public class Category implements DiffIdentifiable {
    private final int id;

    private String name;

    private String emoji;

    public Category(int id, String name, String emoji) {
        this.id = id;
        this.name = name;
        this.emoji = emoji;
    }

    public Category(String name, String emoji) {
        this.name = name;
        this.emoji = emoji;
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    @Override
    public boolean isContentTheSame(Object other) {
        return this.equals(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id &&
                Objects.equals(name, category.name) &&
                Objects.equals(emoji, category.emoji);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, emoji);
    }
}