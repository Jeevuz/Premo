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

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import me.dmdev.premo.PmActivity
import me.dmdev.premo.PmStateSaver
import me.dmdev.premo.Saveable
import me.dmdev.premo.navigation.PmFactory
import me.dmdev.premo.navigation.PmStackChange

class MainActivity : PmActivity<MainPm>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mainScreen(getPresentationModel())
        }
    }

    @Composable
    fun mainScreen(mainPm: MainPm) {
        // Не очень нравится что эта функция в активити. Ее бы как-то завернуть, чтобы написать рутер.дайКонтент()
        // И какую-то связь пмок с скринами. Может через дескрипшны в мапе или еще как. Чтобы в активити меньше кода.
        // Навигация же в пмках, тут только привязка.
        navigation(mainPm.pmStackChanges.bind(PmStackChange.Empty)) { pm -> // bind(PmStackChange.Empty) - странно смотрится
            when (pm) {
                is SamplesPm -> samplesScreen(pm)
                is CounterPm -> counterScreen(pm)
                is CounterUdfPm -> counterUdfScreen(pm)
                is CountdownPm -> countdownScreen(pm)
                is DialogPm -> dialogScreen(pm)
                is BottomBarPm -> bottomBarScreen(pm)
                else -> emptyScreen()
            }
        }
    }

    // Кажется очень незначительным. Типа "разве нельзя как-то узнать что тут этот дескрипшн, если тип <MainPm> указан? Бла-бла"
    // Особенно с учетом того, что потом используется недалеко тут getPresentationModel()
    override fun providePmDescription(): Saveable {
        return MainPm.Description
    }

    // Может в конструкторе активити это выглядело бы лучше? Или вообще где-то еще.
    // Активити тут просто стартовая точка и ей приходится это все подставлять,
    // но может это можно унести в тот же DI или отдельное создание?
    override fun providePmFactory(): PmFactory {
        return MainPmFactory()
    }

    // С этим отдельно надо подумать, что это нужно подставлять тут в активити. Вроде будто бы активити тут вообще не при чем кажется.
    override fun providePmStateSaver(): PmStateSaver {
        return JsonPmStateSaver()
    }
}