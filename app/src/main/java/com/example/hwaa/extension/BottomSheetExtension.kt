package com.example.hwaa.extension
//
//import android.annotation.SuppressLint
//import android.content.res.Resources
//import android.widget.FrameLayout
//import androidx.annotation.IdRes
//import androidx.annotation.LayoutRes
//import androidx.fragment.app.Fragment
//import com.example.hwaa.R
//import com.example.hwaa.databinding.LayoutCreatePostBinding
//import com.google.android.material.bottomsheet.BottomSheetBehavior
//import com.google.android.material.bottomsheet.BottomSheetDialog
//
//
//fun Fragment.showBottomSheetDialog(
//    @LayoutRes layout: Int,
//    @IdRes textViewToSet: Int? = null,
//    textToSet: String? = null,
//    fullScreen: Boolean = true,
//    expand: Boolean = true
//) {
//    val dialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
//    dialog.setOnShowListener {
//        val bottomSheet: FrameLayout =
//            dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)
//                ?: return@setOnShowListener
//        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
//        if (fullScreen && bottomSheet.layoutParams != null) {
//            showFullScreenBottomSheet(bottomSheet)
//        }
//        bottomSheet.setBackgroundColor(resources.getColor(android.R.color.transparent, null))
//        if (!expand) return@setOnShowListener
//        expandBottomSheet(bottomSheetBehavior)
//    }
//
//    @SuppressLint("InflateParams")
//    val view = layoutInflater.inflate(layout, null)
//    val binding = LayoutCreatePostBinding.bind(view)
//    binding.tbCreate.ibBack.setOnClickListener { dialog.dismiss() }
//    dialog.setContentView(view)
//    dialog.show()
//}
//
//private fun showFullScreenBottomSheet(bottomSheet: FrameLayout) {
//    val layoutParams = bottomSheet.layoutParams
//    layoutParams.height = Resources.getSystem().displayMetrics.heightPixels
//    bottomSheet.layoutParams = layoutParams
//}
//
//private fun expandBottomSheet(bottomSheetBehavior: BottomSheetBehavior<FrameLayout>) {
//    bottomSheetBehavior.skipCollapsed = true
//    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//}