import java.util.Comparator;

class drSurnameSort implements Comparator<Doctor>

{
    public int compare(Doctor o1, Doctor o2)
    {
        return  o1.getSurname().compareTo(o2.getSurname());
    }
}

class drFirstNameSort implements Comparator<Doctor>

{
    public int compare(Doctor o1, Doctor o2)
    {
        return  o1.getFirstName().compareTo(o2.getFirstName());
    }
}

