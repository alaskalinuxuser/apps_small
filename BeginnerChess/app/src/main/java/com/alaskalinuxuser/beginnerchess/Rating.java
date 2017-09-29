package com.alaskalinuxuser.beginnerchess;

import static com.alaskalinuxuser.beginnerchess.ThinkTank.flipBoard;
import static com.alaskalinuxuser.beginnerchess.ThinkTank.kingSafe;
import static com.alaskalinuxuser.beginnerchess.ThinkTank.posibleK;

/*  Copyright 2017 by AlaskaLinuxUser (https://thealaskalinuxuser.wordpress.com)
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/

public class Rating {

    public static int rating(int list, int depth) {
        int counter=0, material=rateMaterial();
        //counter+=rateAttack();
        counter+=material;
        counter+=rateMoveablitly(list, depth, material);
        counter+=ratePositional(material);
        flipBoard();
        material=rateMaterial();
        //counter-=rateAttack();
        counter-=material;
        counter-=rateMoveablitly(list, depth, material);
        counter-=ratePositional(material);
        flipBoard();
        return -(counter+depth*50);
    } // End rating

    static int pawnBoard[][]={//attribute to http://chessprogramming.wikispaces.com/Simplified+evaluation+function
        { 0,  0,  0,  0,  0,  0,  0,  0},
        {50, 50, 50, 50, 50, 50, 50, 50},
        {10, 10, 20, 30, 30, 20, 10, 10},
        { 5,  5, 10, 25, 25, 10,  5,  5},
        { 0,  0,  0, 20, 20,  0,  0,  0},
        { 5, -5,-10,  0,  0,-10, -5,  5},
        { 5, 10, 10,-20,-20, 10, 10,  5},
        { 0,  0,  0,  0,  0,  0,  0,  0}};
    static int rookBoard[][]={
        { 0,  0,  0,  0,  0,  0,  0,  0},
        { 5, 10, 10, 10, 10, 10, 10,  5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        { 0,  0,  0,  5,  5,  0,  0,  0}};
    static int knightBoard[][]={
        {-50,-40,-30,-30,-30,-30,-40,-50},
        {-40,-20,  0,  0,  0,  0,-20,-40},
        {-30,  0, 10, 15, 15, 10,  0,-30},
        {-30,  5, 15, 20, 20, 15,  5,-30},
        {-30,  0, 15, 20, 20, 15,  0,-30},
        {-30,  5, 10, 15, 15, 10,  5,-30},
        {-40,-20,  0,  5,  5,  0,-20,-40},
        {-50,-40,-30,-30,-30,-30,-40,-50}};
    static int bishopBoard[][]={
        {-20,-10,-10,-10,-10,-10,-10,-20},
        {-10,  0,  0,  0,  0,  0,  0,-10},
        {-10,  0,  5, 10, 10,  5,  0,-10},
        {-10,  5,  5, 10, 10,  5,  5,-10},
        {-10,  0, 10, 10, 10, 10,  0,-10},
        {-10, 10, 10, 10, 10, 10, 10,-10},
        {-10,  5,  0,  0,  0,  0,  5,-10},
        {-20,-10,-10,-10,-10,-10,-10,-20}};
    static int queenBoard[][]={
        {-20,-10,-10, -5, -5,-10,-10,-20},
        {-10,  0,  0,  0,  0,  0,  0,-10},
        {-10,  0,  5,  5,  5,  5,  0,-10},
        { -5,  0,  5,  5,  5,  5,  0, -5},
        {  0,  0,  5,  5,  5,  5,  0, -5},
        {-10,  5,  5,  5,  5,  5,  0,-10},
        {-10,  0,  5,  0,  0,  0,  0,-10},
        {-20,-10,-10, -5, -5,-10,-10,-20}};
    static int kingMidBoard[][]={
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-20,-30,-30,-40,-40,-30,-30,-20},
        {-10,-20,-20,-20,-20,-20,-20,-10},
        { 20, 20,  0,  0,  0,  0, 20, 20},
        { 20, 30, 10, 10, 10, 10, 30, 20}};
    static int kingEndBoard[][]={
        {-50,-40,-30,-20,-20,-30,-40,-50},
        {-30,-20,-10,  0,  0,-10,-20,-30},
        {-30,-10, 20, 30, 30, 20,-10,-30},
        {-30,-10, 30, 40, 40, 30,-10,-30},
        {-30,-10, 30, 40, 40, 30,-10,-30},
        {-30,-10, 20, 30, 30, 20,-10,-30},
        {-30,-30,  0,  0,  0,  0,-30,-30},
        {-50,-30,-30,-30,-30,-30,-30,-50}};
    public static int rateAttack() {
        int counter=0;
        int tempPositionC=MainActivity.kingPositionC;
        for (int i=0;i<64;i++) {
            switch (MainActivity.chessBoard[i/8][i%8]) {
                case "P": {MainActivity.kingPositionC=i; if (!kingSafe()) {counter-=64;}}
                    break;
                case "R": {MainActivity.kingPositionC=i; if (!kingSafe()) {counter-=500;}}
                    break;
                case "N": {MainActivity.kingPositionC=i; if (!kingSafe()) {counter-=300;}}
                    break;
                case "B": {MainActivity.kingPositionC=i; if (!kingSafe()) {counter-=300;}}
                    break;
                case "Q": {MainActivity.kingPositionC=i; if (!kingSafe()) {counter-=900;}}
                    break;
            }
        }
        MainActivity.kingPositionC=tempPositionC;
        if (!kingSafe()) {counter-=200;}
        return counter/2;
    }
    public static int rateMaterial() {
        int counter=0, bishopCounter=0;
        for (int i=0;i<64;i++) {
            switch (MainActivity.chessBoard[i/8][i%8]) {
                case "P": counter+=100;
                    break;
                case "R": counter+=500;
                    break;
                case "N": counter+=300;
                    break;
                case "B": bishopCounter+=1;
                    break;
                case "Q": counter+=900;
                    break;
            }
        }
        if (bishopCounter>=2) {
            counter+=300*bishopCounter;
        } else {
            if (bishopCounter==1) {counter+=250;}
        }
        return counter;
    }
    public static int rateMoveablitly(int listLength, int depth, int material) {
        int counter=0;
        counter+=listLength;//5 pointer per valid move
        if (listLength==0) {//current side is in checkmate or stalemate
            if (!kingSafe()) {//if checkmate
                counter+=-200000*depth;
            } else {//if stalemate
                counter+=-150000*depth;
            }
        }
        return 0;
    }
    public static int ratePositional(int material) {
        int counter=0;
        for (int i=0;i<64;i++) {
            switch (MainActivity.chessBoard[i/8][i%8]) {
                case "P": counter+=pawnBoard[i/8][i%8];
                    break;
                case "R": counter+=rookBoard[i/8][i%8];
                    break;
                case "N": counter+=knightBoard[i/8][i%8];
                    break;
                case "B": counter+=bishopBoard[i/8][i%8];
                    break;
                case "Q": counter+=queenBoard[i/8][i%8];
                    break;
                case "K": if (material>=1750) {counter+=kingMidBoard[i/8][i%8]; counter+=posibleK(MainActivity.kingPositionC).length()*10;} else
                {counter+=kingEndBoard[i/8][i%8]; counter+=posibleK(MainActivity.kingPositionC).length()*30;}
                    break;
            }
        }
        return counter;
    }
}