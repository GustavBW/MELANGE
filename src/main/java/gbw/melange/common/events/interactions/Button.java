package gbw.melange.common.events.interactions;

public enum Button {
     LEFT(0),
     RIGHT(1),
     MIDDLE(2),
     BACK(3),
     FORWARD(4),
     UNKNOWN(-1);

     public final int code;
     Button(int code){
         this.code = code;
     }

     public static Button valueOf(char c){
          return switch (c) {
               case 0 -> LEFT;
               case 1 -> RIGHT;
               case 2 -> MIDDLE;
               case 3 -> BACK;
               case 4 -> FORWARD;
               default -> UNKNOWN;
          };
     }
     public static Button valueOf(int i){
          return switch (i) {
               case 0 -> LEFT;
               case 1 -> RIGHT;
               case 2 -> MIDDLE;
               case 3 -> BACK;
               case 4 -> FORWARD;
               default -> UNKNOWN;
          };
     }
}
