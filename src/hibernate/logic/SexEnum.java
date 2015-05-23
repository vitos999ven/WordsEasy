package hibernate.logic;



public enum SexEnum {
    BOTH(0), MALE(1), FEMALE(2);
    
    private final byte value;
    
    SexEnum(int value){
        this.value = ((value > 2) || (value < 0))? (byte)0 : (byte)value;
    }
    
    public byte getValue(){
        return value;
    } 
}
