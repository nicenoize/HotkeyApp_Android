package HotKeyApp.model;

import java.awt.*;
import java.awt.event.KeyEvent;
import static multex.MultexUtil.create;

/**
 * HotKeyApp_GUI v0.5
 * #############################
 * @author Alexander Stahl, Sebastian Voigt
 * @version 0.5
 * #############################
 * This Class contains all executable keycodes for programs.
 */
public class Keypresses {
    /** All executable Key for this program */
    private final int
        zero = KeyEvent.VK_0, one = KeyEvent.VK_1, two = KeyEvent.VK_2, three = KeyEvent.VK_3, four = KeyEvent.VK_4,
        five = KeyEvent.VK_5, six = KeyEvent.VK_6, seven = KeyEvent.VK_7, eight = KeyEvent.VK_8, nine = KeyEvent.VK_9,

        // CHARS A-Z
        A = KeyEvent.VK_A, B = KeyEvent.VK_B, C = KeyEvent.VK_C, D = KeyEvent.VK_D, E = KeyEvent.VK_E,
        F = KeyEvent.VK_F, G = KeyEvent.VK_G, H = KeyEvent.VK_H, I = KeyEvent.VK_I, J = KeyEvent.VK_J,
        K = KeyEvent.VK_K, L = KeyEvent.VK_L, M = KeyEvent.VK_M, N = KeyEvent.VK_N, O = KeyEvent.VK_O,
        P = KeyEvent.VK_P, Q = KeyEvent.VK_Q, R = KeyEvent.VK_R, S = KeyEvent.VK_S, T = KeyEvent.VK_T,
        U = KeyEvent.VK_U, V = KeyEvent.VK_V, W = KeyEvent.VK_W, X = KeyEvent.VK_X, Y = KeyEvent.VK_Y,
        Z = KeyEvent.VK_Z,

        // CURSOR
        LEFT = KeyEvent.VK_LEFT, UP = KeyEvent.VK_UP, RIGHT = KeyEvent.VK_RIGHT, DOWN = KeyEvent.VK_RIGHT,

        // MODIFIER
        ALT = KeyEvent.VK_ALT, CONTROL = KeyEvent.VK_CONTROL, SHIFT = KeyEvent.VK_SHIFT, ALT_GRAPH = KeyEvent.VK_ALT_GRAPH,

        // FUNCTION
        F1 = KeyEvent.VK_F1, F2 = KeyEvent.VK_F2, F3 = KeyEvent.VK_F3, F4 = KeyEvent.VK_F4,
        F5 = KeyEvent.VK_F5, F6 = KeyEvent.VK_F6, F7 = KeyEvent.VK_F7, F8 = KeyEvent.VK_F8,
        F9 = KeyEvent.VK_F9, F10 = KeyEvent.VK_F10, F11 = KeyEvent.VK_F11, F12 = KeyEvent.VK_F12,

        // WHITESPACES
        SPACE = KeyEvent.VK_SPACE, ENTER = '\n', TAB = '\t',

        // NON-PRINTABLE
        BEGIN = KeyEvent.VK_BEGIN, END = KeyEvent.VK_END, BACK_SPACE = '\b', DELETE = KeyEvent.VK_DELETE, ESCAPE = KeyEvent.VK_ESCAPE,
        PAGE_DOWN = KeyEvent.VK_PAGE_DOWN, PAGE_UP = KeyEvent.VK_PAGE_UP, PRINTSCREEN = KeyEvent.VK_PRINTSCREEN, WINDOWS = KeyEvent.VK_WINDOWS,

        // OTHER
        MINUS = KeyEvent.VK_MINUS, PLUS = KeyEvent.VK_PLUS, COMMA = KeyEvent.VK_COMMA, ASTERISK = KeyEvent.VK_ASTERISK,
        NUMBER_SIGN = KeyEvent.VK_NUMBER_SIGN, SLASH = KeyEvent.VK_SLASH, PERIOD = KeyEvent.VK_PERIOD, GREATER = KeyEvent.VK_GREATER,

        // IF KEY IS UNDEFINED
        VK_UNDEFINED = KeyEvent.VK_UNDEFINED;

    /** Executes a given KeyCombination.*/
    public void executeKeys(final Hotkey key){
        final String[] keys = key.getKeyCombination().split(" ");

        try {
            final Robot robot = new Robot();

            if(keys.length <= 3){
                // drücken
                for (String keyPress : keys){
                    robot.keyPress(getKeycode(keyPress));
                    robot.delay(20);
                }

                for (String keyRelease : keys){
                    robot.keyRelease(getKeycode(keyRelease));
                    robot.delay(20);
                }
            }

            else {
                for (String keyPress : keys) {
                    robot.keyPress(getKeycode(keyPress));
                    robot.delay(20);
                    robot.keyRelease(getKeycode(keyPress));
                }
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /** Returns
     * @param keyToPress the key that has to be pressed by the robot
     * @return Keycode as int
     */
    private int getKeycode(final String keyToPress){
        switch (keyToPress) {
            // NUMBERS 0-9
            case "zero": return zero;
            case "one": return one;
            case "two": return two;
            case "three": return three;
            case "four": return four;
            case "five": return five;
            case "six": return six;
            case "seven": return seven;
            case "eight": return eight;
            case "nine": return nine;

            // CHARS A-Z
            case "a": return A;
            case "b": return B;
            case "c": return C;
            case "d": return D;
            case "e": return E;
            case "f": return F;
            case "g": return G;
            case "h": return H;
            case "i": return I;
            case "j": return J;
            case "k": return K;
            case "l": return L;
            case "m": return M;
            case "n": return N;
            case "o": return O;
            case "p": return P;
            case "q": return Q;
            case "r": return R;
            case "s": return S;
            case "t": return T;
            case "u": return U;
            case "v": return V;
            case "w": return W;
            case "x": return X;
            case "y": return Y;
            case "z": return Z;

            // CURSOR
            case "left":    return LEFT;
            case "right":   return RIGHT;
            case "up":      return UP;
            case "down":    return DOWN;

            // MODIFIER
            case "alt":    return ALT;
            case "strg":   return CONTROL;
            case "shift":  return SHIFT;
            case "altGr":  return ALT_GRAPH;

            // FUNCTION KEYS
            case "f1":  return F1;
            case "f2":  return F2;
            case "f3":  return F3;
            case "f4":  return F4;
            case "f5":  return F5;
            case "f6":  return F6;
            case "f7":  return F7;
            case "f8":  return F8;
            case "f9":  return F9;
            case "f10": return F10;
            case "f11": return F11;
            case "f12": return F12;

            // WHITESPACES
            case "space": return SPACE;
            case "enter": return ENTER;
            case "tab":   return TAB;

            // NON-PRINTABLE
            case "begin":     return BEGIN;
            case "end":       return END;
            case "backspace": return BACK_SPACE;
            case "del":       return DELETE;
            case "esc":       return ESCAPE;
            case "pageUP":    return PAGE_UP;
            case "pageDOWN":  return PAGE_DOWN;
            case "print":     return PRINTSCREEN;
            case "windows":   return WINDOWS;

            // OTHER
            case "-": return MINUS;
            case "+": return PLUS;
            case ",": return COMMA;
            case "*": return ASTERISK;
            case "#": return NUMBER_SIGN;
            case "/": return SLASH;
            case ".": return PERIOD;
            case "<": return GREATER;

            default:
                return VK_UNDEFINED;
        }
    }

    /**
     * Command: Test if all given Keypresses in this Hotkey are valid
     * @param key the Hotkey to test
     * @return true if all keys are valid
     */
    public boolean isValidKeypress(final Hotkey key){
        final String[] keys = key.getKeyCombination().split(" ");
        for (String keyPress : keys){
            if(getKeycode(keyPress) == VK_UNDEFINED) return false;
        }
        return true;
    }
}