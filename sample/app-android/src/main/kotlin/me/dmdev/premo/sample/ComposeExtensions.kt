/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020-2021 Dmitriy Gorbunov (dmitriy.goto@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.dmdev.premo.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.Flow
import me.dmdev.premo.PresentationModel
import me.dmdev.premo.State
import me.dmdev.premo.navigation.PmStackChange

@Composable
// Назвал бы observedValue, чтобы проще понимать суть компоуза, что вот будет велью, но компоуз будет следить за значением и рекомпоузить
fun <T> State<T>.bind(): T {
    return flow().collectAsState().value
}

@Composable
fun <T> Flow<T>.bind(): T? {
    return collectAsState(null).value
}

@Composable
fun <T> Flow<T>.bind(initialValue: T): T {
    return collectAsState(initialValue).value
}

// Кажется это надо будет унести из семплов в либу?
@Composable
fun navigation(
    pmStackChange: PmStackChange,
    modifier: Modifier = Modifier,
    content: @Composable (PresentationModel?) -> Unit
    // ^Выходит, что именно эта функция передает пмки в экраны.
    // Имхо не оч хорошо, тк она открыта для изменения клиентом и получается уязвима для легкой поломки.
) {

    val stateHolder = rememberSaveableStateHolder()

    // Тут 2 дела склеены - доставание пмки и удаление старого сохраненного стейта. Возможно разделить будет нагляднее и проще в поддержке .
    val pm = when (pmStackChange) {
        is PmStackChange.Push -> {
            stateHolder.removeState(pmStackChange.enterPm.tag) // We push new, remove old just in case
            pmStackChange.enterPm
        }
        is PmStackChange.Pop -> {
            stateHolder.removeState(pmStackChange.exitPm.tag)
            pmStackChange.enterPm
        }
        is PmStackChange.Set -> {
            pmStackChange.pm
        }
        is PmStackChange.Empty -> null
    }

    Box(modifier) {
        stateHolder.SaveableStateProvider(pm?.tag ?: "") {
            content(pm)
        }
    }
}