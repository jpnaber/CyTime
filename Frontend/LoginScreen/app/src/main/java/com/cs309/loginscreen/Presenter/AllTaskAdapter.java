package com.cs309.loginscreen.Presenter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cs309.loginscreen.View.IAllTasksView;
import java.util.List;

/**
 * AllTaskAdapter helps manage the recycler view
 * in the All Task View
 *
 * @author Bofu
 */
public class AllTaskAdapter extends RecyclerView.Adapter {

    private List<String> taskList;  // ArrayList to store the task object
    private IAllTasksView view;     // View class for the AllTaskView

    /**
     * Constructs a new AllTaskAdapter
     * @param view - IAllTaskView view
     */
    public AllTaskAdapter(IAllTasksView view) {
        this.view = view;
    }

    /**
     * Returns a new View Holder for the recycler view
     * @param parent - parent view
     * @param viewType  - view type for recycler view
     * @return - nothing at this point
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    /**
     * Inflate the view holder with items in array list
     * @param holder - the view holder
     * @param position - array list position to show
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    /**
     * Returns the item count in the array list to help inflate the
     * view holder
     * @return - count in the array list
     */
    @Override
    public int getItemCount() {
        return 0;
    }
}
