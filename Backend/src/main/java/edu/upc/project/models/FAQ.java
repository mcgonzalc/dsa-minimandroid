package edu.upc.project.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"date", "question", "answer", "sender"})
public class FAQ {

    String date;
    String question;
    String answer;
    String sender;

    //Constructor with no arguments that allows the serialization of a Item object
    public FAQ() {
    }

    //Constructor that defines the main characteristics of an item
    public FAQ(String date, String question, String answer, String sender) {
        this.setDate(date);
        this.setQuestion(question);
        this.setAnswer(answer);
        this.setSender(sender);
    }

    @XmlElement(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @XmlElement(name = "question")
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @XmlElement(name = "answer")
    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @XmlElement(name = "sender")
    public String getSender() {
        return this.sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "FAQ [date=" + date + ", question=" + question + ", answer=" + answer + ", sender=" + sender + "]";
    }

}
