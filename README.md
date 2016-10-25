Quick Adapter
============

[![Logo](website/logo_ineat.png)][1]

One library to fill quickly your RecyclerView and remove boilerplate code adapter.

# Simple Adapter : Simple cell

```java
@QuickLayout(R.layout.item_example)
public class ExampleQuickItemRenderer extends QuickItemRenderer&lt;MyModel&gt; {

    private TextView mTextView;

    public ExampleQuickItemRenderer(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.txt_label);
    }

    @Override
    public void onBind(int position, MyModel myModel) {
        mTextView.setText(myModel.getLabel());
    }

}

SimpleQuickAdapter&lt;Ineatien, ExampleQuickItemRenderer&gt; adapter =
    new SimpleQuickAdapter&lt;&gt;(values, ExampleQuickItemRenderer.class);
mRecyclerView.setAdapter(adapter);

```

# Multi Cell

```java
@QuickLayout(R.layout.item_cell_one)
public class CellOneQuickItemRenderer extends QuickItemRenderer&lt;MyModel&gt; {

    private TextView mTextView;

    public ExampleQuickItemRenderer(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.txt_label);
    }

    @Override
    public void onBind(int position, MyModel myModel) {
        mTextView.setText(myModel.getLabel());
    }

}

@QuickLayout(R.layout.item_cell_two)
public class CellTwoQuickItemRenderer extends QuickItemRenderer&lt;MyModel&gt; {

    private TextView mTextView;

    public ExampleQuickItemRenderer(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.txt_label);
    }

    @Override
    public void onBind(int position, MyModel myModel) {
        mTextView.setText(myModel.getLabel());
    }

}

QuickAdapter&lt;Ineatien&gt; adapter = new QuickAdapter&lt;&gt;(ineatienList);
adapter.registerItemRenderer(CellOneQuickItemRenderer.class);
adapter.registerItemRenderer(CellTwoQuickItemRenderer.class);
adapter.setQuickAdapterTypeFactory((position, ineatien) -> {
    if ((i % 2) == 0) {
        return CellOneQuickItemRenderer.class;
    }

return CellTwoQuickItemRenderer.class;
});
mRecyclerView.setAdapter(adapter);

```

For documentation and additional information see [the website][2].

Download
--------

Configure your `build.gradle`:

```groovy
dependencies {
    compile 'com.ineat-group:quick-adapter:1.0.0'
}
```

License
-------

    Copyright 2016 Ineat

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


 [1]: http://www.ineat-group.com/
 [2]: https://ineat.github.io/quick-adapter/