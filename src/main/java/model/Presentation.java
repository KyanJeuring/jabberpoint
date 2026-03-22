package model;

import java.util.ArrayList;
import java.util.List;

public class Presentation {
    private String showTitle;
    private ArrayList<Slide> showList = new ArrayList<>();
    private int currentSlideNumber = 0;
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public int getSize() {
        return showList.size();
    }

    public String getTitle() {
        return showTitle;
    }

    public void setTitle(String nt) {
        showTitle = nt;
        notifyObservers();
    }

    public int getSlideNumber() {
        return currentSlideNumber;
    }

    public void setSlideNumber(int number) {
        if (number >= 0 && number < getSize()) {
            currentSlideNumber = number;
            notifyObservers();
        }
    }

    public void prevSlide() {
        if (currentSlideNumber > 0) {
            setSlideNumber(currentSlideNumber - 1);
        }
    }

    public void nextSlide() {
        if (currentSlideNumber < (showList.size() - 1)) {
            setSlideNumber(currentSlideNumber + 1);
        }
    }

    public void clear() {
        showList = new ArrayList<>();
        setSlideNumber(-1);
        notifyObservers();
    }

    public void append(Slide slide) {
        showList.add(slide);
        notifyObservers();
    }

    public Slide getSlide(int number) {
        if (number < 0 || number >= getSize()) {
            return null;
        }
        return showList.get(number);
    }

    public Slide getCurrentSlide() {
        return getSlide(currentSlideNumber);
    }

    public void exit(int n) {
        System.exit(n);
    }
}
