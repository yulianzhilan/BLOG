package dto;

/**
 * Created by scott on 2017/4/17.
 */

import org.springframework.util.StringUtils;

import java.io.Serializable;

public class Sorter implements Serializable {

    private static final long serialVersionUID = 5865149274955936838L;

    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    private String propertyName;

    private boolean descending;

    private String sortType = ASC;

    public Sorter(String propertyName, boolean descending) {
        if(StringUtils.isEmpty(propertyName)) {
            throw new IllegalArgumentException("propertyName must not be empty.");
        }
        this.propertyName = propertyName;
        this.descending = descending;
        this.sortType = this.descending ? DESC : ASC;
    }

    public Sorter(String propertyName) {
        this(propertyName, true);
    }

    public String getPropertyName() {
        return propertyName;
    }

    public static Sorter asc(String propertyName) {
        return new Sorter(propertyName, false);
    }

    public static Sorter desc(String propertyName) {
        return new Sorter(propertyName, true);
    }

    public boolean isAscending() {
        return !isDescending();
    }

    public boolean isDescending() {
        return descending;
    }

    public String getSortType() {
        return this.sortType;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Sorter)) return false;
        Sorter another = (Sorter) obj;
        return (propertyName.equals(another.getPropertyName())) && (descending == another.isDescending());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash*31 + this.getPropertyName().hashCode();
        hash = hash*31 + (descending ? 1 : 2);
        return hash;
    }

    @Override
    public String toString() {
        return this.propertyName + " " + this.sortType;
    }
}