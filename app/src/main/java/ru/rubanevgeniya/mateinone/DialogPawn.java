package ru.rubanevgeniya.mateinone;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class DialogPawn extends DialogFragment {
  protected boolean isWhite;
  protected Figure.Type chosenItem;
  protected CharSequence[] charSequences;
  protected ChessView chessView;
  private CharSequence[] blackItems = new CharSequence[]{"\u265B ", "\u265C ", "\u265D ", "\u265E "};//TODO  can't change to font
  private String title = getResources().getString(R.string.chooseOneItem);

  @Override
  public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
    CharSequence[] whiteItems = new CharSequence[]{"\u2655 ", "\u2656 ", "\u2657 ", "\u2658 "};
    charSequences = blackItems;
    if (isWhite) {
      charSequences = whiteItems;
    }
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setItems(charSequences, new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which) {
        if (which == 0) {
          chosenItem = Figure.Type.queen;
        } else if (which == 1) {
          chosenItem = Figure.Type.rook;
        } else if (which == 2) {
          chosenItem = Figure.Type.bishop;
        } else if (which == 3) {
          chosenItem = Figure.Type.knight;
        }
        afterChoice();
      }
    })
            .setTitle(title);
    return builder.create();
  }

  private void afterChoice() {
    chessView.chosenItem = chosenItem;
    chessView.isNewItemChosen = true;
    chessView.invalidate();
    chessView.afterDialogPawn();
  }
}