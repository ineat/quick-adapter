Quick Adapter
============

[![Logo](website/logo_ineat_small.png)][1]

One library to quickly fill your RecyclerView and remove boilerplate code adapter.

# Simple Adapter : Simple cell

```java
@QuickLayout(R.layout.item_example)
public class ExampleQuickItemRenderer extends QuickItemRenderer<MyModel> {

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

SimpleQuickAdapter<Ineatien, ExampleQuickItemRenderer> adapter =
    new SimpleQuickAdapter<>(values, ExampleQuickItemRenderer.class);
mRecyclerView.setAdapter(adapter);

```

# Multi Cell

```java
@QuickLayout(R.layout.item_cell_one)
public class CellOneQuickItemRenderer extends QuickItemRenderer<MyModel> {

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
public class CellTwoQuickItemRenderer extends QuickItemRenderer<MyModel> {

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

QuickAdapter<Ineatien> adapter = new QuickAdapter<>(ineatienList);
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
    compile 'com.ineat-group:quick-adapter:1.0.1'
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