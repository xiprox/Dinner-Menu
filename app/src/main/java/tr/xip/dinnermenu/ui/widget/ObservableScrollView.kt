/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tr.xip.dinnermenu.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView

/**
 * A custom ScrollView that can accept a scroll listener.
 */
class ObservableScrollView(context: Context, attrs: AttributeSet) : ScrollView(context, attrs) {
    private var callbacks: Callbacks? = null

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (callbacks != null) {
            callbacks!!.onScrollChanged(t)
        }
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (callbacks != null) {
            when (ev.actionMasked) {
                MotionEvent.ACTION_DOWN -> callbacks!!.onDownMotionEvent()
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> callbacks!!.onUpOrCancelMotionEvent()
            }
        }
        return super.onTouchEvent(ev)
    }

    public override fun computeVerticalScrollRange(): Int = super.computeVerticalScrollRange()

    fun setCallbacks(callbacks: Callbacks) {
        this.callbacks = callbacks
    }

    interface Callbacks {
        fun onScrollChanged(scrollY: Int)
        fun onDownMotionEvent()
        fun onUpOrCancelMotionEvent()
    }

    open class CallbacksAdapter : Callbacks {
        override fun onScrollChanged(scrollY: Int) {}
        override fun onDownMotionEvent() {}
        override fun onUpOrCancelMotionEvent() {}
    }
}