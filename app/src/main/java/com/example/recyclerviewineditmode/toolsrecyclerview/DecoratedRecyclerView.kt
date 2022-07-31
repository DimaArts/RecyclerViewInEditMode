package com.example.recyclerviewineditmode.toolsrecyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.withStyledAttributes
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewineditmode.R
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException

class DecoratedRecyclerView: RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.recyclerViewStyle)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        context.withStyledAttributes(attrs, R.styleable.DecoratableInEditModeRecyclerView, defStyleAttr) {
            val decorationsArray = getString(R.styleable.DecoratableInEditModeRecyclerView_decorations)?.split(",")
            decorationsArray?.forEach {
                createDecoration(context, it)
            }
        }
    }

    private fun createDecoration(context: Context, className: String?) {
        if (className != null) {
            val trimmedClassName = className.trim { it <= ' ' }
            if (trimmedClassName.isNotEmpty()) {
                val fullClassName = getFullClassName(context, trimmedClassName)
                try {
                    val classLoader = if (isInEditMode) {
                        this.javaClass.classLoader
                    } else {
                        context.classLoader
                    }
                    val decorationClass = Class.forName(className, false, classLoader)
                        .asSubclass(RecyclerViewDecoration::class.java)
                    var constructor: Constructor<out RecyclerViewDecoration?>
                    try {
                        constructor = decorationClass
                            .getConstructor(*DECORATION_CONSTRUCTOR_SIGNATURE)
                    } catch (e: NoSuchMethodException) {
                        constructor = try {
                            decorationClass.getConstructor()
                        } catch (e1: NoSuchMethodException) {
                            e1.initCause(e)
                            throw IllegalStateException(
                                "Error creating ItemDecoration $className", e1
                            )
                        }
                    }
                    constructor.isAccessible = true

                    val decoration = createEntity(constructor, context)
                    if (decoration != null)
                        addItemDecoration(decoration)
                } catch (e: ClassNotFoundException) {
                    throw IllegalStateException(fullClassName
                            + ": Unable to find ItemDecoration " + className, e);
                } catch (e: InvocationTargetException) {
                    throw IllegalStateException(fullClassName
                            + ": Could not instantiate the ItemDecoration: " + className, e);
                } catch (e: InstantiationException) {
                    throw IllegalStateException(fullClassName
                            + ": Could not instantiate the ItemDecoration: " + className, e);
                } catch (e: IllegalAccessException) {
                    throw IllegalStateException(fullClassName
                            + ": Cannot access non-public constructor " + className, e);
                } catch (e: ClassCastException) {
                    throw IllegalStateException(fullClassName
                            + ": Class is not a ItemDecoration " + className, e);
                }
            }
        }
    }

    private fun <T> createEntity(constructor: Constructor<T>, vararg args: Any) : T {
        return constructor.newInstance(*args)
    }

    private fun getFullClassName(context: Context, className: String): String {
        if (className[0] == '.') {
            return context.packageName + className
        }
        return if (className.contains(".")) {
            className
        } else RecyclerView::class.java.getPackage()?.name + '.' + className
    }

    companion object {
        private val DECORATION_CONSTRUCTOR_SIGNATURE = arrayOf(
            Context::class.java
        )
    }
}