package gun0912.tedimagepicker.builder

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize;
import gun0912.tedimagepicker.builder.listener.OnErrorListener
import gun0912.tedimagepicker.builder.listener.OnMultiSelectedListener
import gun0912.tedimagepicker.builder.listener.OnSelectedListener
import gun0912.tedimagepicker.builder.type.SelectType
import io.reactivex.Single
import io.reactivex.SingleEmitter
import java.lang.ref.WeakReference

@Parcelize
data class SelectedResult(
    public val uri: Uri,
    public val annotate: Boolean
) : Parcelable

@Parcelize
data class SelectedResults(
    public val uris: List<Uri>,
    public val annotate: Boolean
) : Parcelable


class TedRxImagePicker {
    companion object {
        @JvmStatic
        fun with(context: Context) = Builder(WeakReference(context))
    }

    @SuppressLint("ParcelCreator")
    class Builder(private val contextWeakReference: WeakReference<Context>) :
        TedImagePickerBaseBuilder<Builder>() {

        fun start(): Single<SelectedResult> =
            Single.create { emitter ->
                this.onSelectedListener = object : OnSelectedListener {
                    override fun onSelected(result: SelectedResult) {
                        emitter.onSuccess(result)
                    }
                }
                start(SelectType.SINGLE, emitter)
            }


        fun startMultiImage(): Single<SelectedResults> =
            Single.create { emitter ->
                this.onMultiSelectedListener = object : OnMultiSelectedListener {
                    override fun onSelected(results: SelectedResults) {
                        emitter.onSuccess(results)
                    }
                }
                start(SelectType.MULTI, emitter)
            }

        private fun start(selectType: SelectType, emitter: SingleEmitter<*>) {
            this.onErrorListener = object : OnErrorListener {
                override fun onError(throwable: Throwable) {
                    emitter.onError(throwable)
                }
            }
            this.selectType = selectType
            contextWeakReference.get()?.let {
                startInternal(it)
            }

        }
    }


}


