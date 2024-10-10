package ru.normno

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

inline val <T> List<T>.lastItem: T
    get() = get(lastIndex)

@JvmInline
value class Month(val number: Int)

fun main() {
    val list = (1..100).toList()
    println(list.lastItem)

    CoroutineScope(Dispatchers.Default).launch {
        delay(1000L)
        list.inlinedForEach {
            delay(1000L)
            println(it)
        }
    }

    "Hello, World".printClassName()

    list.inlinedForEach {
        println(it)
        return
    }
}

inline fun executeAsync(crossinline action: () -> Unit) {
    CoroutineScope(Dispatchers.Default).launch {
        action()
    }
}

/**
 * Byte code with main function
```
List list = CollectionsKt.toList((Iterable)(new IntRange(1, 100)));
normalForEach(list, MainKt::main$lambda$0);
```
 */
fun <T> List<T>.normalForEach(action: (T) -> Unit) {
    for (item in this) {
        action(item)
    }
}

/**
 * Byte code with main function
```
List list = CollectionsKt.toList((Iterable)(new IntRange(1, 100)));
List $this$inlinedForEach$iv = list;
int $i$f$inlinedForEach = false;
Iterator var3 = $this$inlinedForEach$iv.iterator();

while(var3.hasNext()) {
Object item$iv = var3.next();
int it = ((Number)item$iv).intValue();
int var6 = false;
System.out.println(it);
}
```
 */
inline fun <T> List<T>.inlinedForEach(action: (T) -> Unit) {
    for (item in this) {
        action(item)
    }
}

/**
 * Byte code with main function
```
Object $this$printClassName$iv = "Hello, World";
int $i$f$printClassName = false;
String var3 = Reflection.getOrCreateKotlinClass(String.class).getSimpleName();
System.out.println(var3);
```
 */
inline fun <reified T> T.printClassName() {
    println(T::class.simpleName)
}