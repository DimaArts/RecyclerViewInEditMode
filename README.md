# RecyclerViewInEditMode
Android Studio is a great tool for creating mobile applications, but some simple things don't work in it. One of the most important features is to see the result. This example shows how to bypass view restrictions of RecyclerView in editor (InEditMode).

Multiple layouts in AS editor mode (tools:listItem)
=======

1. Add sampledata folder
2. Add to sampledata folder file (for example teachers_list_sample) with layout that you need

Example:
```xml
@layout/item_title
@layout/item_image
@layout/item_text
@layout/item_title
@layout/item_text
@layout/item_image
@layout/item_text
@layout/item_text
@layout/item_image
@layout/item_title
@layout/item_text
@layout/item_title
@layout/item_image
@layout/item_title
@layout/item_text
@layout/item_image
@layout/item_text
```

Now check the result
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".FirstFragment">
    <androidx.recyclerview.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:listitem="@sample/teachers_list_sample"
        tools:itemCount="17"/>
</androidx.constraintlayout.widget.ConstraintLayout>
```
![result1](https://user-images.githubusercontent.com/7409055/182043775-942be0a8-0194-4cdf-865d-4c1875aa3721.png)

Not working) But it's not the end!

3. Create folder in sampledata (for example teachers) create xml file in this folder with any name

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android" xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content">
    <include tools:layout="@sample/teachers_list_sample"/>
</FrameLayout>
```

Change in xml:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".FirstFragment">
    <androidx.recyclerview.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:itemCount="17"
        tools:listitem="@sample/teachers"/>
</androidx.constraintlayout.widget.ConstraintLayout>
```

Now check the result

![result2](https://user-images.githubusercontent.com/7409055/182043916-09f265d3-c896-4eee-af40-6460a4eb4fcb.png)

Working! But sizes of layouts is wrong, so fix it

4. Create new class

```kotlin
class DynamicSizeLayout : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var maxWidth = 0
        var maxHeight = 0
        val widthSpecSize1 = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecSize1 = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(widthSpecSize1, heightSpecSize1)


        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val lp = child.layoutParams
            val widthSpec = if (lp.width == MATCH_PARENT) MeasureSpec.makeMeasureSpec(
                measuredWidth, MeasureSpec.EXACTLY
            )
            else MeasureSpec.makeMeasureSpec(
                0, MeasureSpec.UNSPECIFIED
            )
            val heightSpec = if (lp.height == MATCH_PARENT) MeasureSpec.makeMeasureSpec(
                measuredHeight, MeasureSpec.EXACTLY
            )
            else MeasureSpec.makeMeasureSpec(
                0, MeasureSpec.UNSPECIFIED
            )
            child.measure(widthSpec, heightSpec)
            val width = child.measuredWidth
            if (lp.width == MATCH_PARENT) {
                maxWidth = Int.MAX_VALUE
            } else if (width > maxWidth) {
                maxWidth = width
            }
            val height = child.measuredHeight
            if (lp.height == MATCH_PARENT) {
                maxHeight = Int.MAX_VALUE
            } else if (height > maxHeight) {
                maxHeight = height
            }
        }

        val widthSpecSize2 = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecSize2 = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(
            if (maxWidth == Int.MAX_VALUE) widthSpecSize2 else maxWidth,
            if (maxHeight == Int.MAX_VALUE) heightSpecSize2 else maxHeight
        )
    }
}
```

Change xml in sampledata folder (change package of DynamicSizeLayout in this sample)

```xml
<?xml version="1.0" encoding="utf-8"?>
<com.example.recyclerviewineditmode.dynamic.DynamicSizeLayout xmlns:android="http://schemas.android.com/apk/res/android" xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content">
    <include tools:layout="@sample/teachers_list_sample"/>
</com.example.recyclerviewineditmode.dynamic.DynamicSizeLayout>
```
Result:

![Result3](https://user-images.githubusercontent.com/7409055/182044143-62d08d1a-954a-47d1-ab7c-4cb1a8596448.png)

Decorations in AS editor mode
=======

1. Create classes:

```kotlin
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
```

and

```kotlin
abstract class RecyclerViewDecoration(context: Context): RecyclerView.ItemDecoration()
```

2. Create decoration and you can use it

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.core.widget.NestedScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".FirstFragment">

    <com.example.recyclerviewineditmode.toolsrecyclerview.DecoratedRecyclerView
        android:id="@+id/recycler"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:decorations="com.example.recyclerviewineditmode.sample.TeachersMarginDecoration"
        app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
        tools:ignore="SpeakableTextPresentCheck"
        tools:itemCount="17"
        tools:listitem="@sample/teachers" />
</androidx.core.widget.NestedScrollView>
```

Result:

![Result4](https://user-images.githubusercontent.com/7409055/182044314-4a4df797-832a-4444-9fe5-10e6c3e5498c.png)
