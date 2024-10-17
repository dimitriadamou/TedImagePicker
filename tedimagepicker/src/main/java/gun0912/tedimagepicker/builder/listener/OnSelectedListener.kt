package gun0912.tedimagepicker.builder.listener

import android.net.Uri
import gun0912.tedimagepicker.builder.SelectedResult

interface OnSelectedListener {
    fun onSelected(result: SelectedResult)
}