package gun0912.tedimagepicker.builder.listener

import android.net.Uri
import gun0912.tedimagepicker.builder.SelectedResults

interface OnMultiSelectedListener {
    fun onSelected(results: SelectedResults)
}