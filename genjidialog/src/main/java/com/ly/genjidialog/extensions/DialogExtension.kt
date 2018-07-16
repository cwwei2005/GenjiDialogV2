package com.ly.genjidialog.extensions

import android.content.DialogInterface
import android.view.KeyEvent
import android.view.View
import com.ly.genjidialog.other.DialogOptions
import com.ly.genjidialog.listener.DialogShowOrDismissListener
import com.ly.genjidialog.GenjiDialog
import com.ly.genjidialog.other.ViewHolder
import com.ly.genjidialog.listener.OnKeyListener
import com.ly.genjidialog.listener.ViewConvertListener

/**
 * 创建一个dialog
 * 你也可以继承GenjiDialog，实现更多功能，并通过扩展函数来简化创建过程
 */
inline fun newGenjiDialog(options: DialogOptions.(dialog: GenjiDialog) -> Unit): GenjiDialog {
    val genjiDialog = GenjiDialog()
    genjiDialog.dialogOptions.options(genjiDialog)
    return genjiDialog
}

/**
 * 当需要通过继承GenjiDialog来分离Activity与dialog的代码时，
 * 可通过该扩展方法在子类中创建DialogOptions，可见AAA
 */
inline fun GenjiDialog.dialogOptionsFun(dialogOp: DialogOptions.() -> Unit): DialogOptions {
    val options = DialogOptions()
    options.dialogOp()
    dialogOptions = options
    return options
}

/**
 * 设置convertListener的扩展方法
 */
inline fun DialogOptions.convertListenerFun(crossinline listener: (view: View, holder: ViewHolder, dialog: GenjiDialog) -> Unit) {
    val viewConvertListener = object : ViewConvertListener() {
        override fun convertView(holder: ViewHolder, dialog: GenjiDialog) {
            listener.invoke(holder.rootView, holder, dialog)
        }
    }
    convertListener = viewConvertListener
}

/**
 * 添加DialogShowOrDismissListener的扩展方法
 */
inline fun DialogOptions.addShowDismissListener(key: String, dialogInterface: DialogShowOrDismissListener.() -> Unit): DialogOptions {
    val dialogShowOrDismissListener = DialogShowOrDismissListener()
    dialogShowOrDismissListener.dialogInterface()
    showDismissMap[key] = dialogShowOrDismissListener
    return this
}

/**
 * 设置OnKeyListener的扩展方法
 */
inline fun DialogOptions.onKeyListenerFun(crossinline listener: (dialog: DialogInterface, keyCode: Int, event: KeyEvent) -> Boolean) {
    val onKey = object : OnKeyListener() {
        override fun onKey(dialog: DialogInterface, keyCode: Int, event: KeyEvent): Boolean {
            return listener.invoke(dialog, keyCode, event)
        }
    }
    onKeyListener = onKey
}
