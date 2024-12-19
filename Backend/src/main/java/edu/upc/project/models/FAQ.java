package edu.upc.project.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"question", "answer"})
public class FAQ {

    String question;
    String answer;

    //Constructor with no arguments that allows the serialization of a Item object
    public FAQ() {
    }

    //Constructor that defines the main characteristics of an item
    public FAQ(String question, String answer) {
        this.setQuestion(question);
        this.setAnswer(answer);
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

    @Override
    public String toString() {
        return "FAQ [question=" + question + ", answer=" + answer + "]";
    }

}
