package com.wooftown.gui

import com.wooftown.core.MyColor
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import javafx.scene.control.Dialog

/**
 * Dialog which says who is win
 * @param color - winner's color
 * @see dialog when you can see who is win and exit from it
 */
class WinnerDialog(color: MyColor?) : Dialog<ButtonType>() {
    init {
        title = "congrats"
        with(dialogPane) {
            headerText = if (color == null){
                "Draw"
            } else{
                "$color is winning"
            }
            buttonTypes.add(ButtonType("back to desk", ButtonBar.ButtonData.OK_DONE))
        }
    }
}
