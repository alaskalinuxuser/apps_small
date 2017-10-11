package com.alaskalinuxuser.gametheory;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static char[] theBoard = {'R','N','B','Q','K','B','N','R','P','P','P','P','P','P','P','P','*','*','*','*',
            '*','*','*','*','*','*','*','*','*','*','*','*','*','*','*','*','*','*','*','*','*','*','*',
            '*','*','*','*','*','p','*','p','p','p','p','p','p','r','n','b','q','k','b','n','r'};
    static boolean wKingNeverMove, wKRNeverMove,wQRNeverMove, bKingNeverMove,bKRNeverMove,bQRNeverMove, whiteTurn;
    static int whiteKing, blackKing;
    static String lastMove = "p4933*", promoteToW = "Q", getPromoteToB = "q";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        wKingNeverMove=true;wKRNeverMove=true;wQRNeverMove=true;
        bKingNeverMove=true;bKRNeverMove=true;bQRNeverMove=true;
        whiteTurn=true;

        // Display the board in the logs....
        String logBoard="";
        for(int i = 0 ; i < 64; i++) {
            logBoard = logBoard + theBoard[i];
        }
        Log.i("WJH", logBoard);

        long a,b;
        a = System.currentTimeMillis();
        allMoves();
        b = System.currentTimeMillis()-a;
        Log.i("WJH", "That took "+ String.valueOf(b) +" ms.");

    } // End on create.

    public static String allMoves() {
        String list = "";
        if (whiteTurn){
            for (int i = 0; i < 64; i++) {
                switch (theBoard[i]) {
                    case 'N': list+=nightMoves(i);break;
                    case 'R': list+=rookMoves(i);break;
                    case 'B': list+=bishopMoves(i);break;
                    case 'Q': list+=queenMoves(i);break;
                    case 'K': list+=kingMoves(i);break;
                    case 'P': list+=pawnMoves(i);break;
                }
            }} else {
            for (int i = 0; i < 64; i++) {
                switch (theBoard[i]) {
                    case 'n': list+=nightMovesB(i);break;
                    case 'r': list+=rookMovesB(i);break;
                    case 'b': list+=bishopMovesB(i);break;
                    case 'q': list+=queenMovesB(i);break;
                    case 'k': list+=kingMovesB(i);break;
                    case 'p': list+=pawnMovesB(i);break;
                }
            }}
        Log.i("WJH", list);
        return list;
        /*
         * The list is in this format 123456,
         * 1 = Moving piece
         * 2,3 = 2 digit from square
         * 4,5 = 2 digit to square
         * 6 = captured piece
         * followed by a comma.
         */
    } // End possible moves.


    public static String nightMovesB(int i) {
        String list = "";
        List<Integer> theseMoves = new ArrayList<Integer>();
        String moveSquare;

        int rowNum = i/8;
        int colNum = i%8;

        if (rowNum < 7 ) {
            if (colNum > 1) {
                theseMoves.add(i + 6);
            }
            if (colNum < 6) {
                theseMoves.add(i+10);
            }
        }
        if (rowNum < 6 ) {
            if (colNum > 0) {
                theseMoves.add(i + 15);
            }
            if (colNum < 7) {
                theseMoves.add(i+17);
            }
        }
        if (rowNum > 0 ) {
            if (colNum < 6) {
                theseMoves.add(i - 6);
            }
            if (colNum > 1) {
                theseMoves.add(i-10);
            }
        }
        if (rowNum > 1 ) {
            if (colNum < 7) {
                theseMoves.add(i - 15);
            }
            if (colNum > 0) {
                theseMoves.add(i-17);
            }
        }

        for(int l=0; l<theseMoves.size();l++) {
            int k = theseMoves.get(l);
            if (Character.isUpperCase(theBoard[k]) || theBoard[k] == '*') {
                moveSquare = String.valueOf(theBoard[k]);
                theBoard[k] = 'n';
                theBoard[i] = moveSquare.charAt(0);
                if (isKingSafe()) {
                    String F = String.valueOf(i);
                    String T = String.valueOf(k);
                    if (i < 10) {
                        F = "0" + F;
                    }
                    if (k < 10) {
                        T = "0" + T;
                    }
                    list = list + "n" + F + T + moveSquare.charAt(0) + ",";
                }
                theBoard[k] = moveSquare.charAt(0);
                theBoard[i] = 'n';
            }
        }
        return list;
    } // End black knight moves.

    public static String rookMovesB(int i) {
        String list = "";
        List<Integer> theseMoves = new ArrayList<Integer>();
        String moveSquare;
        int g = i%8;

        // Up moves
        boolean notI = true;
        int j = 1;
        int vert = 8;
        int k = i;
        if (i < 56) {
            k = i + (vert * j);
        }
        while (theBoard[k] == '*' && notI) {
            theseMoves.add(k);
            vert += 8;
            if (k < 56) {
                k = i + (vert * j);
            } else {
                notI = false;
            }
        } // While it's empty.
        if (Character.isUpperCase(theBoard[k])) {
            theseMoves.add(k);
        } // When there is an enemy.

        // Down moves
        notI = true;
        j = -1;
        vert = 8;
        k = i;
        if (i > 7) {
            k = i + (vert * j);
        }
        while (theBoard[k] == '*' && notI) {
            theseMoves.add(k);
            vert += 8;
            if (k >7) {
                k = i + (vert * j);
            } else {
                notI = false;
            }
        } // While it's empty.
        if (Character.isUpperCase(theBoard[k])) {
            theseMoves.add(k);
        } // When there is an enemy.

        // Right side....
        notI = true;
        int rj = 1;
        int rk = i;
        if (g < 7) {
            rk = i + rj;
        }
        while (theBoard[rk] == '*' && notI) {
            theseMoves.add(rk);
            rj++;
            if (rk%8 < 7) {
                rk = i + rj;
            } else {
                notI = false;
            }
        } // While it's empty.
        if (Character.isUpperCase(theBoard[rk])) {
            theseMoves.add(rk);
        } // When there is an enemy.

        // Left side....
        notI=true;
        rj = 1;
        rk = i;
        if (g > 0) {
            rk = i - rj;
        }
        while (theBoard[rk] == '*' && notI) {
            theseMoves.add(rk);
            rj++;
            if (rk%8 > 0) {
                rk = i - rj;
            } else {
                notI=false;
            }
        } // While it's empty.
        if (Character.isUpperCase(theBoard[rk])) {
            theseMoves.add(rk);
        } // When there is an enemy.

        for(int l=0; l<theseMoves.size();l++) {
            k = theseMoves.get(l);
            moveSquare = String.valueOf(theBoard[k]);
            theBoard[k] = 'r';
            theBoard[i] = moveSquare.charAt(0);
            if (isKingSafe()) {
                String F = String.valueOf(i);
                String T = String.valueOf(k);
                if (i < 10) {
                    F = "0" + F;
                }
                if (k < 10) {
                    T = "0" + T;
                }
                list = list + "r" + F + T + moveSquare.charAt(0) + ",";
            }
            theBoard[k] = moveSquare.charAt(0);
            theBoard[i] = 'r';
        }
        return list;
    } // End black Rook moves.

    public static String bishopMovesB (int i) {
        String list = "";
        List<Integer> theseMoves = new ArrayList<Integer>();
        String moveSquare;
        boolean notI=true;
        int e = i/8;
        int f = i%8;

        int k;
        if (e < 7) {
            // Up diagonal moves.
            if (f < 7) {
                k = i + 9;
                while (theBoard[k] == '*' && notI) {
                    theseMoves.add(k);
                    if (k/8 < 7 && k%8 < 7) {
                        k = k + 9;
                    } else {
                        notI = false;
                    }
                } // While it's empty.
                if (Character.isUpperCase(theBoard[k])) {
                    theseMoves.add(k);
                } // When there is an enemy.
            }
            notI = true;
            if (f > 0) {
                k = i + 7;
                while (theBoard[k] == '*' && notI) {
                    theseMoves.add(k);
                    if (k%8 > 0 && k/8 < 7) {
                        k = k + 7;
                    } else {
                        notI = false;
                    }
                } // While it's empty.
                if (Character.isUpperCase(theBoard[k])) {
                    theseMoves.add(k);
                } // When there is an enemy.
            }
        }

        if (e > 0) {
            // down diagonal moves.
            notI = true;
            if (f > 0) {
                k = i - 9;
                while (theBoard[k] == '*' && notI) {
                    theseMoves.add(k);
                    if (k%8 > 0 && k/8 > 0) {
                        k = k - 9;
                    } else {
                        notI = false;
                    }
                } // While it's empty.
                if (Character.isUpperCase(theBoard[k])) {
                    theseMoves.add(k);
                } // When there is an enemy.
            }
            notI = true;
            if (f < 7) {
                k = i - 7;
                while (theBoard[k] == '*' && notI) {
                    theseMoves.add(k);
                    if (k%8 < 7 && k/8 > 0) {
                        k = k - 7;
                    } else {
                        notI = false;
                    }
                } // While it's empty.
                if (Character.isUpperCase(theBoard[k])) {
                    theseMoves.add(k);
                } // When there is an enemy.
            }
        }

        for(int l=0; l<theseMoves.size();l++) {
            k = theseMoves.get(l);
            moveSquare = String.valueOf(theBoard[k]);
            theBoard[k] = 'b';
            theBoard[i] = moveSquare.charAt(0);
            if (isKingSafe()) {
                String F = String.valueOf(i);
                String T = String.valueOf(k);
                if (i < 10) {
                    F = "0" + F;
                }
                if (k < 10) {
                    T = "0" + T;
                }
                list = list + "b" + F + T + moveSquare.charAt(0) + ",";
            }
            theBoard[k] = moveSquare.charAt(0);
            theBoard[i] = 'b';
        }
        return list;
    } // End Black Bishop moves.

    public static String queenMovesB (int i) {
        // Combined Bishop and Rook set. I could have just called them as is, but then they would
        // be stamped, B and R, instead of Q.
        String list = "";
        List<Integer> theseMoves = new ArrayList<Integer>();
        String moveSquare;
        int g = i%8;

        // Up moves
        boolean notI = true;
        int j = 1;
        int vert = 8;
        int k = i;
        if (i < 56) {
            k = i + (vert * j);
        }
        while (theBoard[k] == '*' && notI) {
            theseMoves.add(k);
            vert += 8;
            if (k < 56) {
                k = i + (vert * j);
            } else {
                notI = false;
            }
        } // While it's empty.
        if (Character.isUpperCase(theBoard[k])) {
            theseMoves.add(k);
        } // When there is an enemy.

        // Down moves
        notI = true;
        j = -1;
        vert = 8;
        k = i;
        if (i > 7) {
            k = i + (vert * j);
        }
        while (theBoard[k] == '*' && notI) {
            theseMoves.add(k);
            vert += 8;
            if (k >7) {
                k = i + (vert * j);
            } else {
                notI = false;
            }
        } // While it's empty.
        if (Character.isUpperCase(theBoard[k])) {
            theseMoves.add(k);
        } // When there is an enemy.

        // Right side....
        notI = true;
        int rj = 1;
        int rk = i;
        if (g < 7) {
            rk = i + rj;
        }
        while (theBoard[rk] == '*' && notI) {
            theseMoves.add(rk);
            rj++;
            if (rk%8 < 7) {
                rk = i + rj;
            } else {
                notI = false;
            }
        } // While it's empty.
        if (Character.isUpperCase(theBoard[rk])) {
            theseMoves.add(rk);
        } // When there is an enemy.

        // Left side....
        notI=true;
        rj = 1;
        rk = i;
        if (g > 0) {
            rk = i - rj;
        }
        while (theBoard[rk] == '*' && notI) {
            theseMoves.add(rk);
            rj++;
            if (rk%8 > 0) {
                rk = i - rj;
            } else {
                notI=false;
            }
        } // While it's empty.
        if (Character.isUpperCase(theBoard[rk])) {
            theseMoves.add(rk);
        } // When there is an enemy.

        notI=true;
        int e = i/8;
        int f = i%8;

        if (e < 7) {
            // Up diagonal moves.
            if (f < 7) {
                k = i + 9;
                while (theBoard[k] == '*' && notI) {
                    theseMoves.add(k);
                    if (k/8 < 7 && k%8 < 7) {
                        k = k + 9;
                    } else {
                        notI = false;
                    }
                } // While it's empty.
                if (Character.isUpperCase(theBoard[k])) {
                    theseMoves.add(k);
                } // When there is an enemy.
            }
            notI = true;
            if (f > 0) {
                k = i + 7;
                while (theBoard[k] == '*' && notI) {
                    theseMoves.add(k);
                    if (k%8 > 0 && k/8 < 7) {
                        k = k + 7;
                    } else {
                        notI = false;
                    }
                } // While it's empty.
                if (Character.isUpperCase(theBoard[k])) {
                    theseMoves.add(k);
                } // When there is an enemy.
            }
        }

        if (e > 0) {
            // down diagonal moves.
            notI = true;
            if (f > 0) {
                k = i - 9;
                while (theBoard[k] == '*' && notI) {
                    theseMoves.add(k);
                    if (k%8 > 0 && k/8 > 0) {
                        k = k - 9;
                    } else {
                        notI = false;
                    }
                } // While it's empty.
                if (Character.isUpperCase(theBoard[k])) {
                    theseMoves.add(k);
                } // When there is an enemy.
            }
            notI = true;
            if (f < 7) {
                k = i - 7;
                while (theBoard[k] == '*' && notI) {
                    theseMoves.add(k);
                    if (k%8 < 7 && k/8 > 0) {
                        k = k - 7;
                    } else {
                        notI = false;
                    }
                } // While it's empty.
                if (Character.isUpperCase(theBoard[k])) {
                    theseMoves.add(k);
                } // When there is an enemy.
            }
        }

        for(int l=0; l<theseMoves.size();l++) {
            k = theseMoves.get(l);
            moveSquare = String.valueOf(theBoard[k]);
            theBoard[k] = 'q';
            theBoard[i] = moveSquare.charAt(0);
            if (isKingSafe()) {
                String F = String.valueOf(i);
                String T = String.valueOf(k);
                if (i < 10) {
                    F = "0" + F;
                }
                if (k < 10) {
                    T = "0" + T;
                }
                list = list + "q" + F + T + moveSquare.charAt(0) + ",";
            }
            theBoard[k] = moveSquare.charAt(0);
            theBoard[i] = 'q';
        }
        return list;
    } // End Black Queen moves.

    public static String kingMovesB (int i) {
        String list = "";
        List<Integer> theseMoves = new ArrayList<Integer>();
        String moveSquare;
        int g = i%8;
        int h = i/8;
        if (h > 0) {
            if (theBoard[i-8] == '*' || Character.isUpperCase(theBoard[i-8])) {
                theseMoves.add(i-8);}
            if (g > 0) {
                if (theBoard[i-9] == '*' || Character.isUpperCase(theBoard[i-9])) {
                    theseMoves.add(i-9);}}
            if (g < 7) {
                if (theBoard[i-7] == '*' || Character.isUpperCase(theBoard[i-7])) {
                    theseMoves.add(i-7);}}}
        if (h < 7) {
            if (theBoard[i+8] == '*' || Character.isUpperCase(theBoard[i+8])) {
                theseMoves.add(i+8);}
            if (g < 7) {
                if (theBoard[i+9] == '*' || Character.isUpperCase(theBoard[i+9])) {
                    theseMoves.add(i+9);}}
            if (g > 0) {
                if (theBoard[i+7] == '*' || Character.isUpperCase(theBoard[i+7])) {
                    theseMoves.add(i+7);}}}
        if (g < 7) {
            if (theBoard[i+1] == '*' || Character.isUpperCase(theBoard[i+1])) {
                theseMoves.add(i+1);}}
        if (g > 0) {
            if (theBoard[i-1] == '*' || Character.isUpperCase(theBoard[i-1])) {
                theseMoves.add(i-1);}}

        // Need castle moves //

        if (bKingNeverMove && isKingSafe()) {
            if (bKRNeverMove && theBoard[61] == '*' && theBoard[62] == '*') {
                blackKing = 61;
                if (isKingSafe()) {
                    blackKing = 62;
                    if (isKingSafe()) {
                        list = list + "K-0-0R,";
                    } else { blackKing = 60; }
                } else { blackKing = 60; }
            }
            if (bQRNeverMove && theBoard[57] == '*' && theBoard[58] == '*' && theBoard[59] == '*') {
                blackKing = 59;
                if (isKingSafe()) {
                    blackKing = 58;
                    if (isKingSafe()) {
                        list = list + "K0-0-0,";
                    } else { blackKing = 60; }
                } else { blackKing = 60; }
            }
        }

        // Castle moves //

        int k;
        for(int l=0; l<theseMoves.size();l++) {
            k = theseMoves.get(l);
            moveSquare = String.valueOf(theBoard[k]);
            theBoard[k] = 'k';
            theBoard[i] = moveSquare.charAt(0);
            if (isKingSafe()) {
                String F = String.valueOf(i);
                String T = String.valueOf(k);
                if (i < 10) {
                    F = "0" + F;
                }
                if (k < 10) {
                    T = "0" + T;
                }
                list = list + "k" + F + T + moveSquare.charAt(0) + ",";
            }
            theBoard[k] = moveSquare.charAt(0);
            theBoard[i] = 'k';
        }
        return list;
    } // End Black King moves.

    public static String pawnMovesB (int i) {
        String list = "";
        List<Integer> theseMoves = new ArrayList<Integer>();
        String moveSquare;
        int g = i%8;
        int h = i/8;

        int k, j;
        k = i - 8; j = i - 16;
        if (h == 6) {
            if (theBoard[k] == '*' && theBoard[j] == '*') {
                // The double step from the home row.
                theseMoves.add(j);
            }
        } else if (h == 3) {
            // The rule of en passant...
            if (lastMove.charAt(0) == 'P') {
                int tempTo = Integer.parseInt(lastMove.substring(3, 5));
                int tempFm = Integer.parseInt(lastMove.substring(1, 3));
                if (tempFm / 8 == 1 && tempTo / 8 == 3) { // They did a double step.
                    if (tempTo == i + 1) { // They are on your right.
                        moveSquare = String.valueOf(theBoard[i - 7]);
                        theBoard[i - 7] = 'p';
                        theBoard[i] = moveSquare.charAt(0);
                        if (isKingSafe()) {
                            list = list + "pen" + String.valueOf(i - 7) + "P,";
                        }
                        theBoard[i - 7] = moveSquare.charAt(0);
                        theBoard[i] = 'p';
                    } else if (tempTo == i - 1) { // They are on your left.
                        moveSquare = String.valueOf(theBoard[i - 9]);
                        theBoard[i - 9] = 'p';
                        theBoard[i] = moveSquare.charAt(0);
                        if (isKingSafe()) {
                            list = list + "pen" + String.valueOf(i - 9) + "P,";
                        }
                        theBoard[i - 9] = moveSquare.charAt(0);
                        theBoard[i] = 'p';
                    }
                }
            } // End en passant....
        } else if (h == 1) {
            // The standard catch for moving one space forward.
            k = i - 8;
            if (theBoard[k] == '*') {
                moveSquare = String.valueOf(theBoard[k]);
                theBoard[k] = 'p';
                theBoard[i] = moveSquare.charAt(0);
                if (isKingSafe()) {
                    list = list + "p" + "u" + getPromoteToB + k + moveSquare.charAt(0) + ",";
                }
                theBoard[k] = moveSquare.charAt(0);
                theBoard[i] = 'p';
            }
            k = i - 9;// Attacking to the left and down.
            if (g > 0 && Character.isUpperCase(theBoard[k])) {
                moveSquare = String.valueOf(theBoard[k]);
                theBoard[k] = 'p';
                theBoard[i] = moveSquare.charAt(0);
                if (isKingSafe()) {
                    list = list + "p" + "l" + getPromoteToB + k + moveSquare.charAt(0) + ",";
                }
                theBoard[k] = moveSquare.charAt(0);
                theBoard[i] = 'p';
            }
            k = i - 7;// Attacking to the right and down.
            if (g < 7 && Character.isUpperCase(theBoard[k])) {
                moveSquare = String.valueOf(theBoard[k]);
                theBoard[k] = 'p';
                theBoard[i] = moveSquare.charAt(0);
                if (isKingSafe()) {
                    list = list + "p" + "r" + getPromoteToB + k + moveSquare.charAt(0) + ",";
                }
                theBoard[k] = moveSquare.charAt(0);
                theBoard[i] = 'p';
            } // End Promotions.
        } // End special pawn moves.

        if (h > 1 && h < 7) {
            // The standard catch for moving one space forward.
            k = i - 8;
            if (theBoard[k] == '*') {
                theseMoves.add(k);
            }
            k = i - 9;// Attacking to the left and down.
            if (g > 0 && Character.isUpperCase(theBoard[k])) {
                theseMoves.add(k);
            }
            k = i - 7;// Attacking to the right and down.
            if (g < 7 && Character.isUpperCase(theBoard[k])) {
                theseMoves.add(k);
            }
        } // End boring pawn moves.

        for(int l=0; l<theseMoves.size();l++) {
            k = theseMoves.get(l);
            moveSquare = String.valueOf(theBoard[k]);
            theBoard[k] = 'p';
            theBoard[i] = moveSquare.charAt(0);
            if (isKingSafe()) {
                String F = String.valueOf(i);
                String T = String.valueOf(k);
                if (i < 10) {
                    F = "0" + F;
                }
                if (k < 10) {
                    T = "0" + T;
                }
                list = list + "p" + F + T + moveSquare.charAt(0) + ",";
            }
            theBoard[k] = moveSquare.charAt(0);
            theBoard[i] = 'p';
        }
        return list;
    } // End black pawn moves.

    public static String nightMoves(int i) {
        String list = "";
        List<Integer> theseMoves = new ArrayList<Integer>();
        String moveSquare;

        int rowNum = i/8;
        int colNum = i%8;

        if (rowNum < 7 ) {
            if (colNum > 1) {
                theseMoves.add(i + 6);
            }
            if (colNum < 6) {
                theseMoves.add(i+10);
            }
        }
        if (rowNum < 6 ) {
            if (colNum > 0) {
                theseMoves.add(i + 15);
            }
            if (colNum < 7) {
                theseMoves.add(i+17);
            }
        }
        if (rowNum > 0 ) {
            if (colNum < 6) {
                theseMoves.add(i - 6);
            }
            if (colNum > 1) {
                theseMoves.add(i-10);
            }
        }
        if (rowNum > 1 ) {
            if (colNum < 7) {
                theseMoves.add(i - 15);
            }
            if (colNum > 0) {
                theseMoves.add(i-17);
            }
        }

        for(int l=0; l<theseMoves.size();l++) {
            int k = theseMoves.get(l);
            if (Character.isLowerCase(theBoard[k]) || theBoard[k] == '*') {
                moveSquare = String.valueOf(theBoard[k]);
                theBoard[k] = 'N';
                theBoard[i] = moveSquare.charAt(0);
                if (isKingSafe()) {
                    String F = String.valueOf(i);
                    String T = String.valueOf(k);
                    if (i < 10) {
                        F = "0" + F;
                    }
                    if (k < 10) {
                        T = "0" + T;
                    }
                    list = list + "N" + F + T + moveSquare.charAt(0) + ",";
                }
                theBoard[k] = moveSquare.charAt(0);
                theBoard[i] = 'N';
            }
        }
        return list;
    } // End knight moves.

    public static String rookMoves(int i) {
        String list = "";
        List<Integer> theseMoves = new ArrayList<Integer>();
        String moveSquare;
        int g = i%8;

        // Up moves
        boolean notI = true;
        int j = 1;
        int vert = 8;
        int k = i;
        if (i < 56) {
            k = i + (vert * j);
        }
        while (theBoard[k] == '*' && notI) {
            theseMoves.add(k);
            vert += 8;
            if (k < 56) {
                k = i + (vert * j);
            } else {
                notI = false;
            }
        } // While it's empty.
        if (Character.isLowerCase(theBoard[k])) {
            theseMoves.add(k);
        } // When there is an enemy.

        // Down moves
        notI = true;
        j = -1;
        vert = 8;
        k = i;
        if (i > 7) {
            k = i + (vert * j);
        }
        while (theBoard[k] == '*' && notI) {
            theseMoves.add(k);
            vert += 8;
            if (k >7) {
                k = i + (vert * j);
            } else {
                notI = false;
            }
        } // While it's empty.
        if (Character.isLowerCase(theBoard[k])) {
            theseMoves.add(k);
        } // When there is an enemy.

        // Right side....
        notI = true;
        int rj = 1;
        int rk = i;
        if (g < 7) {
            rk = i + rj;
        }
        while (theBoard[rk] == '*' && notI) {
            theseMoves.add(rk);
            rj++;
            if (rk%8 < 7) {
                rk = i + rj;
            } else {
                notI = false;
            }
        } // While it's empty.
        if (Character.isLowerCase(theBoard[rk])) {
            theseMoves.add(rk);
        } // When there is an enemy.

        // Left side....
        notI=true;
        rj = 1;
        rk = i;
        if (g > 0) {
            rk = i - rj;
        }
        while (theBoard[rk] == '*' && notI) {
            theseMoves.add(rk);
            rj++;
            if (rk%8 > 0) {
                rk = i - rj;
            } else {
                notI=false;
            }
        } // While it's empty.
        if (Character.isLowerCase(theBoard[rk])) {
            theseMoves.add(rk);
        } // When there is an enemy.

        for(int l=0; l<theseMoves.size();l++) {
            k = theseMoves.get(l);
            moveSquare = String.valueOf(theBoard[k]);
            theBoard[k] = 'R';
            theBoard[i] = moveSquare.charAt(0);
            if (isKingSafe()) {
                String F = String.valueOf(i);
                String T = String.valueOf(k);
                if (i < 10) {
                    F = "0" + F;
                }
                if (k < 10) {
                    T = "0" + T;
                }
                list = list + "R" + F + T + moveSquare.charAt(0) + ",";
            }
            theBoard[k] = moveSquare.charAt(0);
            theBoard[i] = 'R';
        }
        return list;
    } // End Rook moves.

    public static String bishopMoves (int i) {
        String list = "";
        List<Integer> theseMoves = new ArrayList<Integer>();
        String moveSquare;
        boolean notI=true;
        int e = i/8;
        int f = i%8;

        int k;
        if (e < 7) {
            // Up diagonal moves.
            if (f < 7) {
                k = i + 9;
                while (theBoard[k] == '*' && notI) {
                    theseMoves.add(k);
                    if (k/8 < 7 && k%8 < 7) {
                        k = k + 9;
                    } else {
                        notI = false;
                    }
                } // While it's empty.
                if (Character.isLowerCase(theBoard[k])) {
                    theseMoves.add(k);
                } // When there is an enemy.
            }
            notI = true;
            if (f > 0) {
                k = i + 7;
                while (theBoard[k] == '*' && notI) {
                    theseMoves.add(k);
                    if (k%8 > 0 && k/8 < 7) {
                        k = k + 7;
                    } else {
                        notI = false;
                    }
                } // While it's empty.
                if (Character.isLowerCase(theBoard[k])) {
                    theseMoves.add(k);
                } // When there is an enemy.
            }
        }

        if (e > 0) {
            // down diagonal moves.
            notI = true;
            if (f > 0) {
                k = i - 9;
                while (theBoard[k] == '*' && notI) {
                    theseMoves.add(k);
                    if (k%8 > 0 && k/8 > 0) {
                        k = k - 9;
                    } else {
                        notI = false;
                    }
                } // While it's empty.
                if (Character.isLowerCase(theBoard[k])) {
                    theseMoves.add(k);
                } // When there is an enemy.
            }
            notI = true;
            if (f < 7) {
                k = i - 7;
                while (theBoard[k] == '*' && notI) {
                    theseMoves.add(k);
                    if (k%8 < 7 && k/8 > 0) {
                        k = k - 7;
                    } else {
                        notI = false;
                    }
                } // While it's empty.
                if (Character.isLowerCase(theBoard[k])) {
                    theseMoves.add(k);
                } // When there is an enemy.
            }
        }

        for(int l=0; l<theseMoves.size();l++) {
            k = theseMoves.get(l);
            moveSquare = String.valueOf(theBoard[k]);
            theBoard[k] = 'B';
            theBoard[i] = moveSquare.charAt(0);
            if (isKingSafe()) {
                String F = String.valueOf(i);
                String T = String.valueOf(k);
                if (i < 10) {
                    F = "0" + F;
                }
                if (k < 10) {
                    T = "0" + T;
                }
                list = list + "B" + F + T + moveSquare.charAt(0) + ",";
            }
            theBoard[k] = moveSquare.charAt(0);
            theBoard[i] = 'B';
        }
        return list;
    } // End Bishop moves.

    public static String queenMoves (int i) {
        // Combined Bishop and Rook set. I could have just called them as is, but then they would
        // be stamped, B and R, instead of Q.
        String list = "";
        List<Integer> theseMoves = new ArrayList<Integer>();
        String moveSquare;
        int g = i%8;

        // Up moves
        boolean notI = true;
        int j = 1;
        int vert = 8;
        int k = i;
        if (i < 56) {
            k = i + (vert * j);
        }
        while (theBoard[k] == '*' && notI) {
            theseMoves.add(k);
            vert += 8;
            if (k < 56) {
                k = i + (vert * j);
            } else {
                notI = false;
            }
        } // While it's empty.
        if (Character.isLowerCase(theBoard[k])) {
            theseMoves.add(k);
        } // When there is an enemy.

        // Down moves
        notI = true;
        j = -1;
        vert = 8;
        k = i;
        if (i > 7) {
            k = i + (vert * j);
        }
        while (theBoard[k] == '*' && notI) {
            theseMoves.add(k);
            vert += 8;
            if (k >7) {
                k = i + (vert * j);
            } else {
                notI = false;
            }
        } // While it's empty.
        if (Character.isLowerCase(theBoard[k])) {
            theseMoves.add(k);
        } // When there is an enemy.

        // Right side....
        notI = true;
        int rj = 1;
        int rk = i;
        if (g < 7) {
            rk = i + rj;
        }
        while (theBoard[rk] == '*' && notI) {
            theseMoves.add(rk);
            rj++;
            if (rk%8 < 7) {
                rk = i + rj;
            } else {
                notI = false;
            }
        } // While it's empty.
        if (Character.isLowerCase(theBoard[rk])) {
            theseMoves.add(rk);
        } // When there is an enemy.

        // Left side....
        notI=true;
        rj = 1;
        rk = i;
        if (g > 0) {
            rk = i - rj;
        }
        while (theBoard[rk] == '*' && notI) {
            theseMoves.add(rk);
            rj++;
            if (rk%8 > 0) {
                rk = i - rj;
            } else {
                notI=false;
            }
        } // While it's empty.
        if (Character.isLowerCase(theBoard[rk])) {
            theseMoves.add(rk);
        } // When there is an enemy.

        notI=true;
        int e = i/8;
        int f = i%8;

        if (e < 7) {
            // Up diagonal moves.
            if (f < 7) {
                k = i + 9;
                while (theBoard[k] == '*' && notI) {
                    theseMoves.add(k);
                    if (k/8 < 7 && k%8 < 7) {
                        k = k + 9;
                    } else {
                        notI = false;
                    }
                } // While it's empty.
                if (Character.isLowerCase(theBoard[k])) {
                    theseMoves.add(k);
                } // When there is an enemy.
            }
            notI = true;
            if (f > 0) {
                k = i + 7;
                while (theBoard[k] == '*' && notI) {
                    theseMoves.add(k);
                    if (k%8 > 0 && k/8 < 7) {
                        k = k + 7;
                    } else {
                        notI = false;
                    }
                } // While it's empty.
                if (Character.isLowerCase(theBoard[k])) {
                    theseMoves.add(k);
                } // When there is an enemy.
            }
        }

        if (e > 0) {
            // down diagonal moves.
            notI = true;
            if (f > 0) {
                k = i - 9;
                while (theBoard[k] == '*' && notI) {
                    theseMoves.add(k);
                    if (k%8 > 0 && k/8 > 0) {
                        k = k - 9;
                    } else {
                        notI = false;
                    }
                } // While it's empty.
                if (Character.isLowerCase(theBoard[k])) {
                    theseMoves.add(k);
                } // When there is an enemy.
            }
            notI = true;
            if (f < 7) {
                k = i - 7;
                while (theBoard[k] == '*' && notI) {
                    theseMoves.add(k);
                    if (k%8 < 7 && k/8 > 0) {
                        k = k - 7;
                    } else {
                        notI = false;
                    }
                } // While it's empty.
                if (Character.isLowerCase(theBoard[k])) {
                    theseMoves.add(k);
                } // When there is an enemy.
            }
        }

        for(int l=0; l<theseMoves.size();l++) {
            k = theseMoves.get(l);
            moveSquare = String.valueOf(theBoard[k]);
            theBoard[k] = 'Q';
            theBoard[i] = moveSquare.charAt(0);
            if (isKingSafe()) {
                String F = String.valueOf(i);
                String T = String.valueOf(k);
                if (i < 10) {
                    F = "0" + F;
                }
                if (k < 10) {
                    T = "0" + T;
                }
                list = list + "Q" + F + T + moveSquare.charAt(0) + ",";
            }
            theBoard[k] = moveSquare.charAt(0);
            theBoard[i] = 'Q';
        }
        return list;
    } // End Queen moves.

    public static String kingMoves (int i) {
        String list = "";
        List<Integer> theseMoves = new ArrayList<Integer>();
        String moveSquare;
        int g = i%8;
        int h = i/8;
        if (h > 0) {
            if (theBoard[i-8] == '*' || Character.isLowerCase(theBoard[i-8])) {
                theseMoves.add(i-8);}
            if (g > 0) {
                if (theBoard[i-9] == '*' || Character.isLowerCase(theBoard[i-9])) {
                    theseMoves.add(i-9);}}
            if (g < 7) {
                if (theBoard[i-7] == '*' || Character.isLowerCase(theBoard[i-7])) {
                    theseMoves.add(i-7);}}}
        if (h < 7) {
            if (theBoard[i+8] == '*' || Character.isLowerCase(theBoard[i+8])) {
                theseMoves.add(i+8);}
            if (g < 7) {
                if (theBoard[i+9] == '*' || Character.isLowerCase(theBoard[i+9])) {
                    theseMoves.add(i+9);}}
            if (g > 0) {
                if (theBoard[i+7] == '*' || Character.isLowerCase(theBoard[i+7])) {
                    theseMoves.add(i+7);}}}
        if (g < 7) {
            if (theBoard[i+1] == '*' || Character.isLowerCase(theBoard[i+1])) {
                theseMoves.add(i+1);}}
        if (g > 0) {
            if (theBoard[i-1] == '*' || Character.isLowerCase(theBoard[i-1])) {
                theseMoves.add(i-1);}}

        // Need castle moves //

        if (wKingNeverMove && isKingSafe()) {
            if (wKRNeverMove && theBoard[5] == '*' && theBoard[6] == '*') {
                whiteKing = 5;
                if (isKingSafe()) {
                    whiteKing = 6;
                    if (isKingSafe()) {
                        list = list + "K-0-0R,";
                    } else { whiteKing = 4; }
                } else { whiteKing = 4; }
            }
            if (wQRNeverMove && theBoard[1] == '*' && theBoard[2] == '*' && theBoard[3] == '*') {
                whiteKing = 3;
                if (isKingSafe()) {
                    whiteKing = 2;
                    if (isKingSafe()) {
                        list = list + "K0-0-0,";
                    } else { whiteKing = 4; }
                } else { whiteKing = 4; }
            }
        }

        // Castle moves //

        int k;
        for(int l=0; l<theseMoves.size();l++) {
            k = theseMoves.get(l);
            moveSquare = String.valueOf(theBoard[k]);
            theBoard[k] = 'K';
            theBoard[i] = moveSquare.charAt(0);
            if (isKingSafe()) {
                String F = String.valueOf(i);
                String T = String.valueOf(k);
                if (i < 10) {
                    F = "0" + F;
                }
                if (k < 10) {
                    T = "0" + T;
                }
                list = list + "K" + F + T + moveSquare.charAt(0) + ",";
            }
            theBoard[k] = moveSquare.charAt(0);
            theBoard[i] = 'K';
        }
        return list;
    } // End King moves.

    public static String pawnMoves (int i) {
        String list = "";
        List<Integer> theseMoves = new ArrayList<Integer>();
        String moveSquare;
        int g = i%8;
        int h = i/8;

        int k = i + 8, j = i + 16;
        if (h == 1) {
            if (theBoard[k] == '*' && theBoard[j] == '*') {
                // The double step from the home row.
                theseMoves.add(j);}
        } else if (h == 4) {
            // The rule of en passant...
            if (lastMove.charAt(0)=='p') {
                int tempTo = Integer.parseInt(lastMove.substring(3,5));
                int tempFm = Integer.parseInt(lastMove.substring(1,3));
                if (tempFm / 8 == 6 && tempTo / 8 == 4) { // The did a double step.
                    if (tempTo == i + 1) { // They are on your right.
                        moveSquare = String.valueOf(theBoard[i+9]);
                        theBoard[i+9] = 'P';
                        theBoard[i] = moveSquare.charAt(0);
                        if (isKingSafe()) {
                            list = list + "PEN" + String.valueOf(i + 9) + "p,";}
                        theBoard[i+9] = moveSquare.charAt(0);
                        theBoard[i] = 'P';
                    } else if (tempTo == i - 1) { // They are on your left.
                        moveSquare = String.valueOf(theBoard[i+7]);
                        theBoard[i+7] = 'P';
                        theBoard[i] = moveSquare.charAt(0);
                        if (isKingSafe()) {
                            list = list + "PEN" + String.valueOf(i + 7) + "p,";}
                        theBoard[i+7] = moveSquare.charAt(0);
                        theBoard[i] = 'P';
                    }}} // End en passant....
        } else if (h == 6) {
            // The standard catch for moving one space forward.
            k = i + 8;
            if (theBoard[k] == '*') {
                moveSquare = String.valueOf(theBoard[k]);
                theBoard[k] = 'P';
                theBoard[i] = moveSquare.charAt(0);
                if (isKingSafe()) {
                    list = list + "P" + "u" + promoteToW + k + moveSquare.charAt(0) + ",";}
                theBoard[k] = moveSquare.charAt(0);
                theBoard[i] = 'P';}
            k = i + 7;// Attacking to the left and up.
            if (g > 0 && Character.isLowerCase(theBoard[k])) {
                moveSquare = String.valueOf(theBoard[k]);
                theBoard[k] = 'P';
                theBoard[i] = moveSquare.charAt(0);
                if (isKingSafe()) {
                    list = list + "P" + "l" + promoteToW + k + moveSquare.charAt(0) + ",";}
                theBoard[k] = moveSquare.charAt(0);
                theBoard[i] = 'P';}
            k = i + 9;// Attacking to the right and up.
            if (g < 7 && Character.isLowerCase(theBoard[k])) {
                moveSquare = String.valueOf(theBoard[k]);
                theBoard[k] = 'P';
                theBoard[i] = moveSquare.charAt(0);
                if (isKingSafe()) {
                    list = list + "P" + "r" + promoteToW + k + moveSquare.charAt(0) + ",";}
                theBoard[k] = moveSquare.charAt(0);
                theBoard[i] = 'P';} // End Promotions.
        } // End special pawn moves.

        if (h > 0 && h < 6) {
            // The standard catch for moving one space forward.
            k = i + 8;
            if (theBoard[k] == '*') {
                theseMoves.add(k);}
            k = i + 7;// Attacking to the left and up.
            if (g > 0 && Character.isLowerCase(theBoard[k])) {
                theseMoves.add(k);}
            k = i + 9;// Attacking to the right and up.
            if (g < 7 && Character.isLowerCase(theBoard[k])) {
                theseMoves.add(k);}
        } // End boring pawn moves.

        for(int l=0; l<theseMoves.size();l++) {
            k = theseMoves.get(l);
            moveSquare = String.valueOf(theBoard[k]);
            theBoard[k] = 'P';
            theBoard[i] = moveSquare.charAt(0);
            if (isKingSafe()) {
                String F = String.valueOf(i);
                String T = String.valueOf(k);
                if (i < 10) {
                    F = "0" + F;
                }
                if (k < 10) {
                    T = "0" + T;
                }
                list = list + "P" + F + T + moveSquare.charAt(0) + ",";
            }
            theBoard[k] = moveSquare.charAt(0);
            theBoard[i] = 'P';
        }
        return list;
    } // End pawn moves.

    public static boolean isKingSafe() {

        if (whiteTurn){

        } else {

        }

        return true;

    } // End is king safe?


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
