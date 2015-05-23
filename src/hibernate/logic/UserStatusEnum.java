
package hibernate.logic;

  

public enum UserStatusEnum {
   NORMAL(0), BLOCKED(1), DELETED(2);
    
    private final byte value;
    
    UserStatusEnum(int value){
        this.value = ((value > 2) || (value < 0))? (byte)0 : (byte)value;
    }
    
    public byte getValue(){
        return value;
    }  
}
