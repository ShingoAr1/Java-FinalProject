import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PersonData {

    private StringProperty id;
    private StringProperty name;
    private StringProperty category;
    private StringProperty email;
    private StringProperty phone;
    private StringProperty address;
    private StringProperty notes;

    public PersonData(String name, String category, String email,
            String phone, String notes,String id, String address) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleStringProperty(category);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
        this.address = new SimpleStringProperty(address);
        this.notes = new SimpleStringProperty(notes);
    }
    public StringProperty idProperty() {
        return id;
    }
    public void setId(StringProperty id) {
        this.id = id;
    }
    public StringProperty nameProperty() {
        return name;
    }
    public void setName(StringProperty name) {
        this.name = name;
    }
    public StringProperty categoryProperty() {
        return category;
    }
    public void setCategory(StringProperty category) {
        this.category = category;
    }
    public StringProperty emailProperty() {
        return email;
    }
    public void setEmail(StringProperty email) {
        this.email = email;
    }
    public StringProperty phoneProperty() {
        return phone;
    }
    public void setPhone(StringProperty phone) {
        this.phone = phone;
    }
    public StringProperty addressProperty() {
        return address;
    }
    public void setAddress(StringProperty address) {
        this.address = address;
    }
    public StringProperty notesProperty() {
        return notes;
    }
    public void setNotes(StringProperty notes) {
        this.notes = notes;
    }

    
}
