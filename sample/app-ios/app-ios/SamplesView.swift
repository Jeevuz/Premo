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

import SwiftUI
import Common

struct SamplesView: View {
    
    private let pm: SamplesPm
    
    init(pm: SamplesPm) {
        self.pm = pm
    }
    
    var body: some View {
        VStack {
            Button("Counter", action: {
                pm.counterClick.invoke()
            })
            .padding()
            
            Button("Counter UDF", action: {
                pm.counterUdfClick.invoke()
            })
            .padding()
            
            Button("Countdown", action: {
                pm.countdownClick.invoke()
            })
            .padding()
            
            Button("Dialog", action: {
                pm.dialogClick.invoke()
            })
            .padding()
            
            Button("Multistack", action: {
                pm.multistackClick.invoke()
            })
            .padding()
        }
    }
}

struct SamplesView_Previews: PreviewProvider {
    static var previews: some View {
        SamplesView(pm: Stubs.init().samplesPm)
    }
}
