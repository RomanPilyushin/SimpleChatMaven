import java.time.LocalDateTime;

public class Msg {
    private String text;
    private String sender;
    private LocalDateTime dateTime;

    public Msg() {}

    public Msg(String text, String sender, LocalDateTime dateTime) {
        this.text = text;
        this.sender = sender;
        this.dateTime = dateTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Msg msg = (Msg) o;

        if (text != null ? !text.equals(msg.text) : msg.text != null) return false;
        if (sender != null ? !sender.equals(msg.sender) : msg.sender != null) return false;
        return dateTime != null ? dateTime.equals(msg.dateTime) : msg.dateTime == null;

    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "text='" + text + '\'' +
                ", sender='" + sender + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}