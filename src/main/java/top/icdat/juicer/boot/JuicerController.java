package top.icdat.juicer.boot;

import top.icdat.juicer.JuicerActuator;
import top.icdat.juicer.JuicerHandlerFactory;
import top.icdat.juicer.core.JuicerData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author SkyJourney
 */
@Tag(name = "Juicer APIs", description = "Juicer Spring Boot default APIs")
@Controller
@RequestMapping("/juicer")
public class JuicerController {

    private final JuicerActuator juicerActuator;

    private final JuicerHandlerFactory juicerHandlerFactory;

    public JuicerController(JuicerActuator juicerActuator, JuicerHandlerFactory juicerHandlerFactory) {
        this.juicerActuator = juicerActuator;
        this.juicerHandlerFactory = juicerHandlerFactory;
    }

    @Operation(method = "GET or POST", description = "Obtain results for specific handler.")
    @ResponseBody
    @RequestMapping(path = "/{handler}", method = {RequestMethod.GET, RequestMethod.POST})
    public List<JuicerData> getData(@PathVariable String handler) {
        try {
            return juicerActuator.getDataFromHandler(handler);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return juicerActuator.getRuntimeStorage().getJuicerResult(handler);
        }
    }

    @Operation(method = "GET or POST", description = "Refresh results for specific handler.")
    @ResponseBody
    @RequestMapping(path = "/refresh/{handler}", method = {RequestMethod.GET, RequestMethod.POST})
    public String refreshData(@PathVariable String handler) {
        try {
            juicerActuator.refreshDataFromHandler(handler);
            return "Success";
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    @Operation(method = "POST", description = "Obtain the list of handler.")
    @ResponseBody
    @RequestMapping(path = "/handlers", method = RequestMethod.POST)
    public Set<String> getHandlers() {
        return juicerHandlerFactory.getHandlerSet();
    }

    @Operation(method = "GET", description = "Show the summary page of Juicer for String Boot")
    @RequestMapping(method = RequestMethod.GET)
    public String getHandlersView(final ModelMap model) {
        List<JuicerHandlerInfo> juicerHandlerInfos = new ArrayList<>();
        for(String handler: juicerHandlerFactory.getHandlerSet()) {
            String previous = juicerHandlerFactory.getJuicerHandler(handler).getParent();
            juicerHandlerInfos.add(new JuicerHandlerInfo(handler, "".equals(previous)?"-":previous));
        }
        model.addAttribute("handlers", juicerHandlerInfos);
        return "juicer/handler";
    }

}
