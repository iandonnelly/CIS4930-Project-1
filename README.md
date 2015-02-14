# CIS4930-Project-1

DroidIRC is an Android-based IRC server and corresponding client application. 

Functionality included:
  Basic sending and receiving messages
  Support for channels using swipeable tabs
  Support for requesting a list of connected users
  Action Icons
  Text coloring

Min SDK: API 21

Overall Structure
  The application architecture was split into two primary layers. The domain and presentation layers. 
  The presentation layer was further divided in a model, view, and presenter as per the MVP design. 
  Thus the presentation layer consisted of all code related to the UI. The domain layer, on the other hand, 
  was essentially the backend of the application and consisted of all server and client classes as well as 
  classes to interact with the server and client.
  
Design structure
 This architecture was selected in order to easily accommodate changes in requirements. That is, since 
 both developers had little prior experience with socket programming the requirements of the application were 
 difficult to define prior to implementation and thus could change rapidly. Additionally, this architecture 
 provided a simple manner in which to divide responsibilities of the application. One developer could focus on the 
 UI, or presentation layer, while the other focused on the backend, or domain layer.
    
Presentation Layer (UI)
  Overall Structure
    The presentation layer was divide into three primary sections. The model, view, and presenter. The views 
    job was to display graphics without any logic. Presenter jobs was take the input from the view and decide 
    what to do with it. Because of the limited the scope of the application, the model was excluded from the 
    design.
    
    The model was represented by the ClientModel and ServerModel classes. These classes were used to store the
    list of channels and their respective message logs. 
    
    The view was represented by the activites and fragments and dialogs. They classes were used only for displaying 
    information or receiving input.
    
    The presenter was represented by the ServerPresenter and ClientPreseenter classes. These classes handled the logic
    of deciding what to do for a given user input, what should be shown on the views, and also updating the ServerModel 
    and ClientModel respectively
    
    
Domain Layer (Backend)
  Server
    Spawns a thread to start the server
    Spawns a thread for each client
        Presenter called when thread receives a message to update the local ServerModel
        Observer design pattern is used to update the UI along with handlers to ensure update is on UI thread.
    Channels
      Client thread is associated with a group where each group represents a channel.
      Messages sent on a channel are broadcast to all members of a group.
        
  Client
    Spawn a thread for the server connection
        Presenter called when thread receives a message to update the local ClientModel
        Observer design pattern is used to update the UI along with handlers to ensure update is on UI thread.
        
  Interactors
    The interactor classes are the interface between the domain layer and the presentation layer. All interaction 
    is made through these classes.

