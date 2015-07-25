package ru.rubanevgeniya.mylockscreen;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class DialogPawn extends DialogFragment {
    protected boolean isWhite;
    protected boolean isNewItemChosen = false;
    protected Figure.Type chosenItem;
    protected CharSequence[] charSequences;
    protected ChessView chessView;
    private CharSequence[] blackItems = new CharSequence[]{"\u265B ","\u265C ","\u265D ","\u265E "};
    private  String title = getResources().getString(R.string.chooseOneItem);
    private static String TAG = "Log = ";

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        CharSequence[] whiteItems = new CharSequence[]{"\u2655 ","\u2656 ","\u2657 ","\u2658 "};
        charSequences = blackItems;
        if(isWhite){
            charSequences = whiteItems;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder//.setMessage("choose one item")

                .setItems(charSequences, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            chosenItem = Figure.Type.queen;
                            afterChoice();
                        } else if (which == 1) {
                            chosenItem = Figure.Type.rook;
                            afterChoice();
                        } else  if (which == 2) {
                            chosenItem = Figure.Type.bishop;
                            afterChoice();
                        } else if (which == 3) {
                            chosenItem = Figure.Type.knight;
                            afterChoice();
                        }
                    }
                })

        .setTitle(title);
        return builder.create();
    }

    private void afterChoice(){
        Log.d(TAG, "chosen item = " + chosenItem);
        chessView.chosenItem = chosenItem;
        isNewItemChosen = true;
        Log.d(TAG,"isNewItemChosen = "+isNewItemChosen);
        chessView.isNewItemChosen = isNewItemChosen;
        chessView.invalidate();
        chessView.afterDialogPawn();
    }
}